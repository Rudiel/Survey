package com.gloobe.survey.Actividades;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gloobe.survey.Modelos.Models.Request.Login;
import com.gloobe.survey.Modelos.Models.Response.User;
import com.gloobe.survey.R;
import com.gloobe.survey.Interfaces.SurveyInterface;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rudielavilaperaza on 11/19/16.
 */

public class Actividad_Login extends AppCompatActivity {

    private EditText etPass, etUsuario;
    private Button btLogin;
    private TextInputLayout tilUsuario, tilPassword;
    private ImageView ivLogo;
    private ProgressBar pbLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        etPass = (EditText) findViewById(R.id.etPassword);
        etUsuario = (EditText) findViewById(R.id.etUsuario);
        btLogin = (Button) findViewById(R.id.btLoginCred);

        tilUsuario = (TextInputLayout) findViewById(R.id.tilUsuario);
        tilPassword = (TextInputLayout) findViewById(R.id.tilPassword);

        ivLogo = (ImageView) findViewById(R.id.ivLoginLogo);
        pbLogin = (ProgressBar) findViewById(R.id.pbLogin);

        Glide.with(this).load(R.drawable.splash_logo).into(ivLogo);


        etPass.setText("qwerty");
        etUsuario.setText("admin@dynamicsurveys.com");

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pbLogin.setVisibility(View.VISIBLE);

                if (passValido() && usuarioValido()) {


                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(getString(R.string.url_global))
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    SurveyInterface service = retrofit.create(SurveyInterface.class);

                    Login login = new Login();
                    login.setEmail(etUsuario.getText().toString());
                    login.setPassword(etPass.getText().toString());

                    Call<User> loginCall = service.login(login);

                    loginCall.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {

                            if (response.body() != null) {
                                User usuario = response.body();

                                Gson gson = new Gson();
                                Intent intent = new Intent(Actividad_Login.this, Actividad_Principal.class);
                                intent.putExtra("Usuario", gson.toJson(usuario));

                                pbLogin.setVisibility(View.GONE);

                                startActivity(intent);
                            } else {
                                //mostar error
                                pbLogin.setVisibility(View.GONE);
                                showDialog(getString(R.string.login_dialogo_titulo), getString(R.string.login_dialog_text_mal));
                            }


                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            //mostrar error
                            showDialog(getString(R.string.login_dialogo_titulo), getString(R.string.login_dialog_text_mal));
                            pbLogin.setVisibility(View.GONE);
                        }
                    });


                }
            }
        });

    }

    private boolean usuarioValido() {
        if (etUsuario.getText().toString().trim().isEmpty()) {
            tilUsuario.setError(getString(R.string.login_hint_usuario_error));
            requestFocus(etUsuario);
            return false;
        } else {
            tilUsuario.setErrorEnabled(false);
        }

        return true;
    }

    private boolean passValido() {
        if (etPass.getText().toString().trim().isEmpty()) {
            tilPassword.setError(getString(R.string.login_hint_password_error));
            requestFocus(etPass);
            return false;
        } else {
            tilPassword.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    /*selectedAccount = Utils.gson.fromJson(getIntent().getExtras()
                .getString("selectedAccount"), Accounts.class);*/

    /*
    *ArrayList<Accounts> aux = Utils.gson.fromJson(jsonResponse,
					new TypeToken<ArrayList<Accounts>>() {
					}.getType()); */

    private void showDialog(String titulo, String mensaje) {

        final Dialog dialog = new Dialog(Actividad_Login.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_dialog_custom);

        Window window = dialog.getWindow();

        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setBackgroundDrawableResource(R.color.survey_dialog);

        TextView tvTitulo = (TextView) dialog.findViewById(R.id.tvDialogTitulo);
        tvTitulo.setText(titulo);

        TextView tvTexto = (TextView) dialog.findViewById(R.id.tvDialogText);
        tvTexto.setText(mensaje);

        Button dialogButton = (Button) dialog.findViewById(R.id.btDialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}

