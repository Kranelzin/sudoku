package com.example.sudoku;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.sudoku.R;

public class Dialogos {

    public interface CallbackDialogo {
        void callbackDialogo(String jogada, Button botao, Posicao posicao);
    }

    private static CallbackDialogo callback;

    public static void coletarJogada(Context context, Button botao, Posicao posicao){
        LayoutInflater layout = LayoutInflater.from(context);

        callback = (CallbackDialogo) context;
        TableLayout tabela = new TableLayout(context);
        AlertDialog.Builder dialogo = new AlertDialog.Builder(context);

        dialogo.setTitle("Selecione um Número: ");

        dialogo.setPositiveButton("Ok", (dialog, which) ->  {
            for(int i = 0; i < 3; i++){
                TableRow linhaTabela = new TableRow(context);
                for(int j = 0; j < 3; j++){
                    Button botaoNumero = new Button(context);
                    botaoNumero.setText(i+1);
                    botaoNumero.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            callback.callbackDialogo(botaoNumero.getText().toString(), botao, posicao);
                        }
                    });
                    linhaTabela.addView(botaoNumero);
                }
                tabela.addView(linhaTabela);
            }

        });
        dialogo.setNegativeButton("Cancelar", (dialog, which) -> {
            dialog.dismiss();
        });
        dialogo.show();
    }

    public static void informar(Context context, String mensagem){
        informar(context, "Atenção", mensagem, null);
    }

    public static void informar(Context context, String titulo, String mensagem){
        informar(context, titulo, mensagem, null);
    }

    public static void informar(Context context, String titulo, String mensagem, DialogInterface.OnClickListener listener){
        new AlertDialog
                .Builder(context)
                .setTitle(titulo)
                .setMessage( mensagem )
                .setCancelable(false)
                .setNeutralButton("Ok", listener)
                .show();
    }
}
