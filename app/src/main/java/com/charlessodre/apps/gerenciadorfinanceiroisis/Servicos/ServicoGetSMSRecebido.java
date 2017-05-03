package com.charlessodre.apps.gerenciadorfinanceiroisis.Servicos;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.charlessodre.apps.gerenciadorfinanceiroisis.util.ReceberSMS;

/**
 * Created by charl on 25/04/2017.
 */

public class ServicoGetSMSRecebido extends IntentService {

    /**
     * A constructor is required, and must call the super IntentService(String)
     * constructor with a name for the worker thread.
     */
    public ServicoGetSMSRecebido() {
        super("ServicoGetSMSRecebido");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent,flags,startId);
    }

    /**
     * The IntentService calls this method from the default worker thread with
     * the intent that started the service. When this method returns, IntentService
     * stops the service, as appropriate.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        // Normally we would do some work here, like download a file.
        // For our sample, we just sleep for 5 seconds.
        try {
            ReceberSMS receberSMS = new ReceberSMS();
            receberSMS.onReceive(this,intent);

            Toast.makeText(this, "service starting onHandleIntent", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            // Restore interrupt status.4
            Toast.makeText(this, "ERRO " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Thread.currentThread().interrupt();
        }
    }
}
