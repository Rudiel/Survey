package com.gloobe.survey.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gloobe.survey.Actividades.Actividad_Principal;
import com.gloobe.survey.Modelos.Auth;
import com.gloobe.survey.Modelos.Authorize;
import com.gloobe.survey.Modelos.Cliente;
import com.gloobe.survey.R;
import com.gloobe.survey.Interfaces.SurveyInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rudielavilaperaza on 26/07/16.
 */
public class Fragment_Login extends Fragment {

    private EditText etPass, etUsuario;
    private Button btLogin;
    private ProgressDialog progressDialog;
    private TextInputLayout tilUsuario, tilPassword;
    private ImageView ivLogo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        etPass = (EditText) getActivity().findViewById(R.id.etPassword);
        etUsuario = (EditText) getActivity().findViewById(R.id.etUsuario);
        btLogin = (Button) getActivity().findViewById(R.id.btLoginCred);

        tilUsuario = (TextInputLayout) getActivity().findViewById(R.id.tilUsuario);
        tilPassword = (TextInputLayout) getActivity().findViewById(R.id.tilPassword);

        ivLogo = (ImageView) getActivity().findViewById(R.id.ivLoginLogo);

        Glide.with(getActivity()).load(R.drawable.logo_name).into(ivLogo);

        progressDialog = new ProgressDialog(getActivity(), R.style.MyTheme);
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

                                ((Actividad_Principal) getActivity()).datos = cliente.getData();
                                //((Actividad_Principal) getActivity()).surveyList = cliente.getData().getSurveys();
                                progressDialog.dismiss();
                                ((Actividad_Principal) getActivity()).iniciarFragment(new Fragment_Lista(), true,((Actividad_Principal)getActivity()).FG_LISTA);

                            } else {
                                progressDialog.dismiss();
                                ((Actividad_Principal) getActivity()).mostarDialogo(getResources().getString(R.string.login_credenciales_invalidas), getResources().getString(R.string.login_dialog_titulo));
                            }
                        }

                        @Override
                        public void onFailure(Call<Cliente> call, Throwable t) {
                            progressDialog.dismiss();
                            ((Actividad_Principal) getActivity()).mostarDialogo(t.getMessage().toString(), getResources().getString(R.string.login_dialog_titulo));
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
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}
