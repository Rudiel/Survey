package com.gloobe.survey.Actividades;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gloobe.survey.Modelos.Auth;
import com.gloobe.survey.Modelos.Authorize;
import com.gloobe.survey.Modelos.Cliente;
import com.gloobe.survey.R;
import com.gloobe.survey.SurveyInterface;
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
    private ProgressDialog progressDialog;
    private TextInputLayout tilUsuario, tilPassword;
    private ImageView ivFondo, ivLogo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        etPass = (EditText) findViewById(R.id.etPassword);
        etUsuario = (EditText) findViewById(R.id.etUsuario);
        btLogin = (Button) findViewById(R.id.btLoginCred);

        tilUsuario = (TextInputLayout) findViewById(R.id.tilUsuario);
        tilPassword = (TextInputLayout) findViewById(R.id.tilPassword);

        ivFondo = (ImageView) findViewById(R.id.ivFondoLogin);
        ivLogo = (ImageView) findViewById(R.id.ivLoginLogo);

        Glide.with(this).load(R.drawable.fondo).centerCrop().into(ivFondo);
        Glide.with(this).load(R.drawable.logo_name).into(ivLogo);

        progressDialog = new ProgressDialog(this, R.style.MyTheme);
        progressDialog.setCancelable(false);

        etPass.setText("qwerty");
        etUsuario.setText("Client1");

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (passValido() && usuarioValido()) {


                    progressDialog.show();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(getString(R.string.url_global))
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    SurveyInterface service = retrofit.create(SurveyInterface.class);

                    Authorize auth = new Authorize();

                    Auth loginRequest = new Auth();
                    loginRequest.setUsername(etUsuario.getText().toString());
                    loginRequest.setPassword(etPass.getText().toString());

                    auth.setLoginRequest(loginRequest);


                    Call<Cliente> clienteCall = service.getClients(auth);
                    clienteCall.enqueue(new Callback<Cliente>() {
                        @Override
                        public void onResponse(Call<Cliente> call, Response<Cliente> response) {

                            if (response.body() != null) {
                                Cliente cliente = response.body();

                                Gson gson = new Gson();

                                Intent intent = new Intent(Actividad_Login.this, Actividad_Principal.class);
                                intent.putExtra("Cliente", gson.toJson(cliente));
                                startActivity(intent);

                                 /*selectedAccount = Utils.gson.fromJson(getIntent().getExtras()
                .getString("selectedAccount"), Accounts.class);*/

    /*
    *ArrayList<Accounts> aux = Utils.gson.fromJson(jsonResponse,
					new TypeToken<ArrayList<Accounts>>() {
					}.getType()); */

                                // ((Actividad_Principal) getActivity()).datos = cliente.getData();
                                // ((Actividad_Principal) getActivity()).surveyList = cliente.getData().getSurveys();


                                progressDialog.dismiss();
                                // ((Actividad_Principal) getActivity()).iniciarFragment(new Fragment_Lista(), true);

                            } else {
                                progressDialog.dismiss();
                                //   ((Actividad_Principal) getActivity()).mostarDialogo(getResources().getString(R.string.login_credenciales_invalidas), getResources().getString(R.string.login_dialog_titulo));
                            }
                        }

                        @Override
                        public void onFailure(Call<Cliente> call, Throwable t) {
                            progressDialog.dismiss();
                            // ((Actividad_Principal) getActivity()).mostarDialogo(t.getMessage().toString(), getResources().getString(R.string.login_dialog_titulo));
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


}

