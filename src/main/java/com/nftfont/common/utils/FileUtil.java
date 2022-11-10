package com.nftfont.common.utils;

import org.springframework.web.multipart.MultipartFile;


import java.io.*;


public class FileUtil {


    public static File multipartToFile(MultipartFile multipartFile) throws IOException {
        File convFile = new File(multipartFile.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        return convFile;
    }

//    public static File svg2png(File file) throws IOException, TranscoderException {
//        String s = file.toURI().toURL().toString();
//        ByteArrayOutputStream resultByteStream = new ByteArrayOutputStream();
//
//        TranscoderInput transcoderInput = new TranscoderInput(s);
//        TranscoderOutput transcoderOutput = new TranscoderOutput(resultByteStream);
//
//        PNGTranscoder pngTranscoder = new PNGTranscoder();
//        pngTranscoder.addTranscodingHint(SVGAbstractTranscoder.KEY_HEIGHT, 256f);
//        pngTranscoder.addTranscodingHint(SVGAbstractTranscoder.KEY_WIDTH, 256f);
//        pngTranscoder.transcode(transcoderInput, transcoderOutput);
//
//        resultByteStream.flush();
//
//        BufferedImage read = ImageIO.read(new ByteArrayInputStream(resultByteStream.toByteArray()));
//        File pngFile = new File(UUID.randomUUID()+".png");
//        ImageIO.write(read,"png",pngFile);
////        pngFile.delete();
//        return pngFile;
//    }
}
