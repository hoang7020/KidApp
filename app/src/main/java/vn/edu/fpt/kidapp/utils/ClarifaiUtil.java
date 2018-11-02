package vn.edu.fpt.kidapp.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;
import java.util.concurrent.TimeUnit;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.request.model.PredictRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.ModelVersion;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Prediction;
import okhttp3.OkHttpClient;

public class ClarifaiUtil {

    private static final String TAG = ClarifaiUtil.class.getSimpleName();

    private static final String API_KEY = "1544ba6a0f794ae6883d31b5445260cf";

    private ClarifaiClient client;
    private List<ClarifaiOutput<Prediction>> result;

    public ClarifaiUtil() {
        client = new ClarifaiBuilder(API_KEY)
                .client(new OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .build()
                )
                .buildSync();
    }

    public void predictImage(final byte[] imageFile, final Context context) {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //predict with default model
//                model<Concept> generalModel = client.getDefaultModels().generalModel();
//                PredictRequest < Concept > request = generalModel.predict().withInputs(
//                        ClarifaiInput.forImage(imageFile)
//                );
//                result = request.executeSync().get();

//                    WorkflowPredictRequest rq = client.workflowPredict("hand")
//                            .withInputs(
//                                    ClarifaiInput.forImage(imageFile)
//                            );
//                    ClarifaiResponse<WorkflowPredictResult> rs = rq.executeSync();
//
//                    Intent intent = new Intent();
//                    intent.setAction("ACTION_PREDICT_SUCCESS");
//                    intent.putExtra("result1", rs.get().workflowResults().get(0).predictions().get(0).start().get(0).asConcept().name());
//                    intent.putExtra("result2", rs.get().workflowResults().get(0).predictions().get(0).start().get(1).asConcept().name());
//                    intent.putExtra("result3", rs.get().workflowResults().get(0).predictions().get(0).start().get(2).asConcept().name());

                    //predict with model
                    ModelVersion modelVersion = client.getModelVersionByID("General", "aa9ca48295b37401f8af92ad1af0d91d")
                            .executeSync()
                            .get();
                    PredictRequest<Prediction> request = client.predict("General")
                            .withVersion(modelVersion)
                            .withInputs(ClarifaiInput.forImage(imageFile));
                    result = request.executeSync().get();
                    Intent intent = new Intent();
                    intent.setAction("ACTION_PREDICT_SUCCESS");
                    intent.putExtra("result1", result.get(0).data().get(0).asConcept().name());
                    intent.putExtra("result2", result.get(0).data().get(1).asConcept().name());
                    intent.putExtra("result3", result.get(0).data().get(2).asConcept().name());
                    context.sendBroadcast(intent);
                } catch (Exception e) {
                    Log.e(TAG, "run: " + e.getMessage());
                }
            }
        });
        t.start();
    }
}
