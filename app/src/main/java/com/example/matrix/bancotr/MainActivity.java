package com.example.matrix.bancotr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import res.layout.DepositoActivity;
import res.layout.Signin;
import res.layout.login;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);







        ListView list = (ListView) findViewById(R.id.listView1);

        ArrayList<String> options = new ArrayList<>();
        options.add(" >>> Acessar Conta");
        options.add(" >>> Criar Conta");
        options.add(" >>> Realizar Depósito");

        ListAdapter listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,options);

        list.setAdapter(listAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch(position){
                    case 0 :
                            AcessarConta();
                        break;
                    case 1 :
                            CriarConta();
                        break;
                    case 2 :
                        DepositoConta();
                        break;
                }
            }
        });

    }

    private void AcessarConta() {
        Intent intent = new Intent(this,login.class);
        startActivity(intent);
    }

    private void CriarConta() {
        Intent intent = new Intent(this,Signin.class);
        startActivity(intent);
    }

    private void DepositoConta() {
        Intent intent = new Intent(this,DepositoActivity.class);
        startActivity(intent);
    }
}
