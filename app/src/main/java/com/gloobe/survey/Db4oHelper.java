package com.gloobe.survey;

import android.content.Context;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import com.gloobe.survey.Modelos.ObjectToSend;

import java.io.IOException;

/**
 * Created by rudielavilaperaza on 11/08/16.
 */
public class Db4oHelper {

    private static ObjectContainer container = null;
    private Context context;

    public Db4oHelper(Context context) {
        this.context = context;
    }

    public ObjectContainer db() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (container == null || container.ext().isClosed()) {
                    container = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), db4oFullPath(context));
                }
            }
        }).start();
        return container;
    }


    private EmbeddedConfiguration dbConfig() throws IOException {
        EmbeddedConfiguration configuration = Db4oEmbedded.newConfiguration();
        configuration.common().objectClass(ObjectToSend.class).objectField("id").indexed(true);
        configuration.common().objectClass(ObjectToSend.class).cascadeOnActivate(true);
        configuration.common().objectClass(ObjectToSend.class).cascadeOnUpdate(true);
        return configuration;
    }

    private String db4oFullPath(Context ctx) {
        return ctx.getDir("data", 0) + "/" + "survey.db4o";
        //this.getDir("data", 0) + "/" + "events.db4o";
    }

    public void close() {
        if (container != null)
            container.close();
    }


}
