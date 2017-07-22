package res.layout;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.matrix.bancotr.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

import Entities.LoginEntity;

import static android.widget.Toast.LENGTH_LONG;

public class login extends AppCompatActivity {


    public static String msg2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        final EditText agencia = (EditText)  findViewById(R.id.agenciaField);
        final EditText conta = (EditText)  findViewById(R.id.contaField);
        final EditText senha = (EditText)  findViewById(R.id.passField);
        Button button = (Button) findViewById(R.id.buttonLogin);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                //Scanner teclado = new Scanner(System.in)
                connect(agencia,conta,senha);

                openMenu();
            }
        });

    }
    public static String msg;
    private static volatile String alpha = null;
    public static volatile LoginEntity loginEntity = null;
    public static LoginEntity loginEntityFULL = null;
    private void connect(final EditText agencia,final EditText conta,final EditText senha){

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    Socket cliente = new Socket("192.168.43.187", 12346);
                    System.out.println("O cliente se conectou ao servidor!");
                    ObjectOutputStream saidaObj = new ObjectOutputStream(cliente.getOutputStream());

                    LoginEntity login = new LoginEntity();

                    login.setAgencia(Integer.parseInt(String.valueOf(agencia.getText())));
                    login.setConta(String.valueOf(conta.getText()));
                    login.setSenha(String.valueOf(senha.getText()));

                    saidaObj.writeObject(login);
                    saidaObj.flush();
                    saidaObj.reset();



                    ObjectInputStream objI = new ObjectInputStream(cliente.getInputStream());
                    Object aux = objI.readObject();


                    if(aux instanceof LoginEntity){
                      MenuM2.LOGIN = (LoginEntity) aux;
                    }
                    alpha = "Agencia: " +MenuM2.LOGIN.getAgencia();
                    setMessage(alpha);
                    //leitor.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

        while (alpha == null);
        Toast.makeText(login.this,getMessage(),Toast.LENGTH_LONG).show();

    }
    private void setMessage(String message){
        alpha = message;
    }

    private String getMessage(){
        return alpha;
    }

    private void openMenu() {
        Intent intent = new Intent(this,MenuM2.class);
        startActivity(intent);
    }


}
