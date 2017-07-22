package res.layout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matrix.bancotr.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import Entities.LoginEntity;
import Entities.Saque;
import Entities.Transferencia;

public class MenuM2 extends AppCompatActivity {

    public static LoginEntity LOGIN = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_m2);

        TabHost host = (TabHost) findViewById(R.id.tabHost);
        host.setup();

        //Tab1
        TabHost.TabSpec spec = host.newTabSpec("Tab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Saldo");
        host.addTab(spec);

        TabHost.TabSpec spec2 = host.newTabSpec("Tab2");
        spec2.setContent(R.id.tab2);
        spec2.setIndicator("Saque");
        host.addTab(spec2);

        TabHost.TabSpec spec3 = host.newTabSpec("Tab3");
        spec3.setContent(R.id.tab3);
        spec3.setIndicator("Transferência");
        host.addTab(spec3);

        TextView saldoValor = (TextView) findViewById(R.id.valorSaldo);
        TextView contaValor = (TextView) findViewById(R.id.contaValor);

        //connect(contaValor,saldoValor);


        double amount = new Double(String.valueOf(LOGIN.getSaldo()));
        Toast.makeText(MenuM2.this,"SALDO: "+amount,Toast.LENGTH_SHORT).show();
        saldoValor.setText(""+amount);
        contaValor.setText(String.valueOf(LOGIN.getConta()));



        Button button = (Button) findViewById(R.id.buttonTrans);
        Toast.makeText(MenuM2.this,"Bem Vindo!",Toast.LENGTH_LONG).show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                EditText valor = (EditText) findViewById(R.id.vtField);
                EditText contaTrans = (EditText) findViewById(R.id.contaTrans);



                transfer(contaTrans,valor);

            }
        });


        Button button2 = (Button) findViewById(R.id.buttonSaq);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText valor = (EditText) findViewById(R.id.vsField);

                Saque(valor);

            }
        });

    }


    private static volatile LoginEntity loginEntity = login.loginEntity;
    private static volatile Object alpha = null;

    private static volatile String beta = null;
    private void transfer(final EditText contaTrans,final EditText valor){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Socket cliente = null;
                    try {
                        cliente = new Socket("192.168.43.187",12346);

                        Transferencia trans = new Transferencia();

                        trans.setContaA(String.valueOf(LOGIN.getConta()));
                        trans.setContaB(String.valueOf(contaTrans.getText()));

                        double amount = new Double(String.valueOf(valor.getText()));
                        trans.setValor(amount);


                        ObjectOutputStream output = new ObjectOutputStream(cliente.getOutputStream());
                        output.writeObject(trans);
                        output.flush();
                        output.reset();


                        BufferedReader buffer = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                        beta = buffer.readLine();



                    } catch (IOException e) {
                        e.printStackTrace();
                    }



                }
            });
        thread.start();

        while (beta == null);
        Toast.makeText(MenuM2.this,""+beta,Toast.LENGTH_SHORT).show();


    }

    public void Saque(final EditText valor){
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                Socket cliente = null;

                try {
                    cliente = new Socket("192.168.43.187",12346);
                    Saque sq = new Saque();
                    double amount = new Double(String.valueOf(valor.getText()));
                    sq.setValor(amount);
                    sq.setConta(LOGIN.getConta());

                    ObjectOutputStream output = new ObjectOutputStream(cliente.getOutputStream());
                    output.writeObject(sq);
                    output.flush();
                    output.reset();


                    BufferedReader buffer = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                    beta = buffer.readLine();

                } catch (IOException e) {
                    e.printStackTrace();
                }



            }
        });
        thread.start();

        while (beta == null);
        Toast.makeText(MenuM2.this,""+beta,Toast.LENGTH_SHORT).show();

    }


    private void setMessage(String message){
        alpha = message;
    }

    private Object getMessage(){
        return alpha;
    }


}
