package com.example.gen_quest.service;

import com.example.gen_quest.repository.SubjectRepository;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;


public class OcrService {

    public String image2text(String imageFile){
        JSONArray field = request(imageFile);
        String text = parse_text(field);
        return text;
    }
    public String parse_text(JSONArray field){
        String res = "";
        JSONObject word;
        int field_len = field.size();
        for(int i=0;i<field_len;i++){
            word = (JSONObject) field.get(i);
            res = res + word.get("inferText").toString() + " ";
            if ((boolean)word.get("lineBreak") == true)
                res = res + "\n";
        }
        return res;
    }

    public JSONArray request(String imageFile) {
        String apiURL = "";
        String secretKey = "";


        try {
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setReadTimeout(30000);
            con.setRequestMethod("POST");
            String boundary = "----" + UUID.randomUUID().toString().replaceAll("-", "");
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            con.setRequestProperty("X-OCR-SECRET", secretKey);

            JSONObject json = new JSONObject();
            json.put("version", "V2");
            json.put("requestId", UUID.randomUUID().toString());
            json.put("timestamp", System.currentTimeMillis());
            JSONObject image = new JSONObject();
            image.put("format", "jpg");
            image.put("name", "demo");
            JSONArray images = new JSONArray();
            images.add(image);
            json.put("images", images);
            String postParams = json.toString();

            con.connect();
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            long start = System.currentTimeMillis();
            ClassPathResource resource = new ClassPathResource(imageFile);
            InputStream inputStream = new ClassPathResource(imageFile).getInputStream();
            File file = File.createTempFile("test", ".PNG");
            try {
                FileUtils.copyInputStreamToFile(inputStream, file);
            } finally {
                IOUtils.closeQuietly(inputStream);
            }
//            File file = resource.getFile();
            writeMultiPart(wr, postParams, file, boundary);
            wr.close();

            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            String res = response.toString();
            System.out.println(res);
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(res);
            JSONObject jobj = (JSONObject) obj;
            JSONArray field = (JSONArray) jobj.get("images");
            field = ((JSONArray)((JSONObject)field.get(0)).get("fields"));

            System.out.println(response);
            return field;
        } catch (Exception e) {
            System.out.println("오류 발생 ocrService에서");
            System.out.println(e);
        }
        return new JSONArray();
    }

    private static void writeMultiPart(OutputStream out, String jsonMessage, File file, String boundary) throws
            IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("--").append(boundary).append("\r\n");
        sb.append("Content-Disposition:form-data; name=\"message\"\r\n\r\n");
        sb.append(jsonMessage);
        sb.append("\r\n");

        out.write(sb.toString().getBytes("UTF-8"));
        out.flush();

        if (file != null && file.isFile()) {
            out.write(("--" + boundary + "\r\n").getBytes("UTF-8"));
            StringBuilder fileString = new StringBuilder();
            fileString
                    .append("Content-Disposition:form-data; name=\"file\"; filename=");
            fileString.append("\"" + file.getName() + "\"\r\n");
            fileString.append("Content-Type: application/octet-stream\r\n\r\n");
            out.write(fileString.toString().getBytes("UTF-8"));
            out.flush();

            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[8192];
                int count;
                while ((count = fis.read(buffer)) != -1) {
                    out.write(buffer, 0, count);
                }
                out.write("\r\n".getBytes());
            }

            out.write(("--" + boundary + "--\r\n").getBytes("UTF-8"));
        }
        out.flush();
    }
}
