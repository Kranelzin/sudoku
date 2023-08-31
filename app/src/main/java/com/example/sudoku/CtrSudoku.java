package com.example.sudoku;

import java.util.Arrays;
import java.util.Random;

public class CtrSudoku {

    static int numeroAleatorio = new Random().nextInt(Integer.MAX_VALUE);
    static int[][] selecionarNumeroZeros = selecionarNumerosParaRetirar();
    static int[][] tabuleiro = montarTabuleiro(escolherMatriz(numeroAleatorio));
    static int[][] tabuleiroCompleto = escolherMatriz(numeroAleatorio);
    static int[][] jogadasErradas = new int[9][9];


    public static void preencherJogada(String jogada, Posicao posicao) {
        for(int x = 0; x < tabuleiro.length; x++){
            for(int j = 0; j < tabuleiro[x].length; j++){
                if(x == posicao.posicaoLinha && j == posicao.posicaoColuna){
                    tabuleiro[x][j] = Integer.parseInt(jogada);
                    jogadasErradas[x][j] = tabuleiro[x][j] == tabuleiroCompleto[x][j] ? 2 : 1;
                    return;
                }
            }
        }
        return;
    }

    public static int[][] getJogadasErradas(){
        return jogadasErradas;
    }

    public static boolean validarJogada(String jogada) {
        if(jogada.length() != 1)
            return false;
        char c = jogada.charAt(0);

        if(!Character.isDigit(c))
            return false;

        return true;
    }

    public static int [][] escolherMatriz(int x){

        final int n = 3;
        final int[][] field = new int[n*n][n*n];

        for(int i = 0; i < n; i++, x++) {
            for(int j = 0; j < n; j++, x+=n) {
                for(int k = 0; k < n*n; k++, x++)
                    field[n*i+j][k] = (x % (n*n)) + 1;
            }
        }
        return field;

    }

    public static int[][] montarTabuleiro(int[][] matriz){
        int p = 0;
        for(int x = 0; x < matriz.length; x++){
            if(p > selecionarNumeroZeros.length)
                break;
            for(int j = 0; j < matriz[x].length; j++){
                for (int m = 0; m < selecionarNumeroZeros[p].length; m++){
                    if(matriz[x][j] == selecionarNumeroZeros[p][m]){
                        matriz[x][j] = 0;
                        break;
                    }
                }
            }
            p++;
        }
        return matriz;
    }

    public static int[][] selecionarNumerosParaRetirar() {

        int delimitador = new Random().nextInt(2) + 4;
        int [] numerosSelecionados = new int[delimitador];
        int [][] matrizNumerosSelecionados = new int[9][9];
        boolean numeroJaInserido = false;
        boolean inseriuNumero = false;

        for(int j = 0; j < 9; j++) {
            numerosSelecionados = new int[delimitador];
            for(int x = 0; x < delimitador; x++) {
                inseriuNumero = false;

                while(!inseriuNumero) {
                    numeroJaInserido = false;
                    int numeroParaRetirar =  new Random().nextInt(8) + 1;

                    for(int p = 0; p < delimitador; p++) {
                        if(numerosSelecionados[p] == numeroParaRetirar) {
                            numeroJaInserido = true;
                            break;
                        }
                    }

                    if(!numeroJaInserido) {
                        numerosSelecionados[x] = numeroParaRetirar;
                        numeroJaInserido = false;
                        inseriuNumero = true;
                    }
                }
            }
            matrizNumerosSelecionados[j] = numerosSelecionados;
        }

        return matrizNumerosSelecionados;
    }

    public static boolean veriificarVitoria() {
        if(Arrays.deepEquals(tabuleiro, tabuleiroCompleto))
            return true;
        else
            return false;
    }

    public static int[][] getTabuleiro() {
        return tabuleiro;
    }

    public static void dica() {
        for(int i = 0; i < tabuleiro.length; i++) {
            for(int j = 0; j < tabuleiro[i].length; j++) {
                if(tabuleiro[i][j] == 0) {
                    tabuleiro[i][j] = tabuleiroCompleto[i][j];
                    return;
                }
            }
        }
    }

    public static void resetar() {
        tabuleiro = montarTabuleiro(escolherMatriz(numeroAleatorio));
    }

    public static void novoJogo() {
        numeroAleatorio = new Random().nextInt(Integer.MAX_VALUE);
        selecionarNumeroZeros = selecionarNumerosParaRetirar();
        tabuleiro = montarTabuleiro(escolherMatriz(numeroAleatorio));
        jogadasErradas = new int[9][9];
        tabuleiroCompleto = escolherMatriz(numeroAleatorio);


    }

}
