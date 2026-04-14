package com.gameshadow.app;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Define a tela inicial (verifique se o nome do layout está correto)
        setContentView(R.layout.activity_main);

        // Chama a função para extrair os arquivos assim que o app abre
        extrairComponentes();
    }

        public void extrairComponentes() {
        String[] pastas = {"box64", "dxvk", "turnip", "wined3d", "vkd3d"};
        String caminhoInterno = getFilesDir().getAbsolutePath() + "/usr/components";

        // 1. Extrair os arquivos .msi da raiz
        try {
            String[] arquivosRaiz = getAssets().list("components");
            if (arquivosRaiz != null) {
                for (String nomeArquivo : arquivosRaiz) {
                    if (nomeArquivo.endsWith(".msi")) {
                        File destino = new File(caminhoInterno + "/" + nomeArquivo);
                        if (!destino.exists()) {
                            InputStream in = getAssets().open("components/" + nomeArquivo);
                            OutputStream out = new FileOutputStream(destino);
                            byte[] buffer = new byte[1024];
                            int ler;
                            while ((ler = in.read(buffer)) != -1) out.write(buffer, 0, ler);
                            in.close(); out.close();
                        }
                    }
                }
            }
        } catch (IOException e) { android.util.Log.e("GameShadow", "Erro MSI", e); }

        // 2. Extrair as pastas dos componentes
        for (String pasta : pastas) {
            try {
                String[] arquivos = getAssets().list("components/" + pasta);
                if (arquivos == null) continue;
                File dirDestino = new File(caminhoInterno + "/" + pasta);
                if (!dirDestino.exists()) dirDestino.mkdirs();
                for (String nome : arquivos) {
                    File destino = new File(dirDestino, nome);
                    if (!destino.exists()) {
                        InputStream in = getAssets().open("components/" + pasta + "/" + nome);
                        OutputStream out = new FileOutputStream(destino);
                        byte[] buffer = new byte[1024];
                        int ler;
                        while ((ler = in.read(buffer)) != -1) out.write(buffer, 0, ler);
                        in.close(); out.close();
                    }
                }
            } catch (IOException e) { android.util.Log.e("GameShadow", "Erro pasta", e); }
        }
    }

