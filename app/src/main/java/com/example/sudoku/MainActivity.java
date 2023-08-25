package com.example.sudoku;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;


import com.example.sudoku.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

public class MainActivity extends AppCompatActivity implements Dialogos.CallbackDialogo {

    private ActivityMainBinding binding;
    TableLayout tabela;

    @Override
    public void callbackDialogo(String jogada, Button botao, Posicao posicao) {
        if(CtrSudoku.validarJogada(jogada)){
            CtrSudoku.preencherJogada(jogada, posicao);
            setTabuleiro();
        }
        else
            Dialogos.informar(this,"Jogada Inválida!");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setTabuleiro();

        setSupportActionBar(binding.toolbar);
    }

    public void setTabuleiro() {

        if(CtrSudoku.veriificarVitoria())
            Dialogos.informar(MainActivity.this,"Vitória!");

        tabela = new TableLayout(this);

        int[][] tabuleiro = CtrSudoku.getTabuleiro();

        for (int x = 0; x < tabuleiro.length; x++) {
            TableRow linhaTabela = new TableRow(this);
            int [][] jogadasErradas = CtrSudoku.getJogadasErradas();
            for (int j = 0; j < tabuleiro[x].length; j++) {
                Button botao = new Button(this);
                if(tabuleiro[x][j] == 0)
                    botao.setText("?");
                else{
                    if(jogadasErradas[x][j] == 2)
                        botao.setTextColor(Color.BLUE);
                    if(jogadasErradas[x][j] == 1)
                        botao.setTextColor(Color.RED);

                    botao.setText(Integer.toString(tabuleiro[x][j]));
                }

                Posicao posicao = new Posicao();
                posicao.posicaoLinha = x;
                posicao.posicaoColuna = j;

                botao.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if(botao.getText().toString().contentEquals("?") || jogadasErradas[posicao.posicaoLinha][posicao.posicaoColuna] == 1)
                            Dialogos.coletarJogada(MainActivity.this,botao,posicao);
                    }
                });

                linhaTabela.addView(botao);
            }
            tabela.addView(linhaTabela);

        }

        if(CtrSudoku.veriificarVitoria())
            Dialogos.informar(MainActivity.this,"Vitória!");

        tabela.setShrinkAllColumns(true);
        tabela.setStretchAllColumns(true);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout linearLayout = new LinearLayout(this);

        binding.getRoot().setLayoutParams(lp);
        tabela.setLayoutParams(lp);

        if(binding.getRoot().getParent() != null) {
            ((ViewGroup)binding.getRoot().getParent()).removeView(binding.getRoot());
        }
        linearLayout.addView(binding.getRoot());

        if(tabela.getParent() != null) {
            ((ViewGroup)tabela.getParent()).removeView(tabela);
        }
        linearLayout.addView(tabela);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        setContentView(linearLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.novojogo) {
            CtrSudoku.novoJogo();
            setTabuleiro();
            return true;
        }
        if (id == R.id.resetar) {
            CtrSudoku.resetar();
            setTabuleiro();
            return true;
        }
        if (id == R.id.dica) {
            CtrSudoku.dica();
            setTabuleiro();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

}