package br.java.meuheroiapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class RequestHandler {

    // Método para enviar httpPostRequest
    // Este método usa dois argumentos
    // O primeiro argumento é a URL do script para o qual enviaremos a solicitação
    // Outro é um HashMap com pares de nome e valor contendo os dados a serem enviados com a solicitação
    public String sendPostRequest(String requestURL, HashMap<String, String> postDataParams) {
        // Criação de um URL
        URL url;

        // Objeto StringBuilder para armazenar a mensagem recuperada do servidor
        StringBuilder sb = new StringBuilder();

        try {
            // Inicializando Url
            url = new URL(requestURL);

            // Criação de uma conexão httmlurl
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Configurando propriedades de conexão
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            // Criação de um fluxo de saída
            OutputStream os = conn.getOutputStream();

            // Escrevendo parâmetros para o pedido
            // Estamos usando um método getPostDataString que é definido abaixo
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                sb = new StringBuilder();
                String response;

                // Lendo a resposta do servidor
                while((response = br.readLine()) != null) {
                    sb.append(response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public String sendGetRequest(String requestURL) {
        StringBuilder sb = new StringBuilder();

        try {
            URL url = new URL(requestURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String s;
            while((s = bufferedReader.readLine()) != null) {
                sb.append(s + "\n");
            }
        } catch (Exception e) {

        }
        return sb.toString();
    }
    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for(Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;

            else
                result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }
}
