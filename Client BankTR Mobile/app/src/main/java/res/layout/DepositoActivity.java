package res.layout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.matrix.bancotr.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Entities.Deposito;

public class DepositoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposito);


        Button but = (Button) findViewById(R.id.buttonDep);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText conta = (EditText) findViewById(R.id.contaDep);
                EditText valor = (EditText) findViewById(R.id.valorDep);

                deposito(conta,valor);
            }
        });

    }


    private static volatile String alpha = null;
    public void deposito(final EditText conta, final EditText valor){
            Thread thread = new Thread(new Runnable() {
                Socket cliente = null;
                @Override
                public void run() {
                    try {
                        cliente = new Socket("192.168.43.187",12346 );
                        Deposito dep = new Deposito();
                        dep.setConta(String.valueOf(conta.getText()));
                        double amount = new Double(String.valueOf(valor.getText()));
                        dep.setValor(amount);


                        ObjectOutputStream output = new ObjectOutputStream(cliente.getOutputStream());
                        output.writeObject(dep);
                        output.flush();
                        output.reset();


                        BufferedReader buffer = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                        alpha = buffer.readLine();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }




                }
            });
        thread.start();
        while(alpha==null);
        Toast.makeText(DepositoActivity.this, alpha, Toast.LENGTH_SHORT).show();
    }
}
