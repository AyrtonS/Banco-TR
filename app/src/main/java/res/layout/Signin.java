package res.layout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.matrix.bancotr.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import Entities.LoginEntity;

public class Signin extends AppCompatActivity {

    public static String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        final EditText agencia = (EditText) findViewById(R.id.editText5);
        final EditText conta = (EditText) findViewById(R.id.editText6);
        final EditText senha = (EditText) findViewById(R.id.editText8);

        Button button = (Button) findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connect(agencia,conta,senha);
            }
        });

    }


    private static volatile LoginEntity loginEntity = null;
    private static volatile String alpha = null;

    private void connect(final EditText agencia,final EditText conta,final EditText senha){

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    Socket cliente = new Socket("192.168.43.187",12349);
                    System.out.println("O cliente se conectou ao servidor!");

                    ObjectOutputStream saidaObj = new ObjectOutputStream(cliente.getOutputStream());

                    LoginEntity login = new LoginEntity();

                    login.setAgencia(Integer.parseInt(String.valueOf(agencia.getText())));
                    login.setConta(String.valueOf(conta.getText()));
                    login.setSenha(String.valueOf(senha.getText()));
                    Double saldo = 100.0;
                    login.setSaldo(Double.parseDouble(String.valueOf(saldo.toString())));

                    saidaObj.writeObject(login);
                    saidaObj.flush();
                    saidaObj.reset();

                    BufferedReader leitor = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                    alpha = leitor.readLine();

                    //setMessage(alpha);



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

        while (alpha == null);
        Toast.makeText(Signin.this,getMessage(),Toast.LENGTH_LONG).show();

    }












    private void setMessage(String message){
        alpha = message;
    }

    private String getMessage(){
        return alpha;
    }


}
