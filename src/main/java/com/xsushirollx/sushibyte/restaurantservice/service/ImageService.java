package com.xsushirollx.sushibyte.restaurantservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class ImageService implements MultipartFile {

    @Autowired
    MultipartFile MyMultipartFile;

    String fileName;

    public ImageService(String image) {
        this.setFileName(image);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String getName() {
        return this.getFileName();
    }

    @Override
    public String getOriginalFilename() {
        return this.getFileName();
    }

    @Override
    public String getContentType() {

        return getFileName().substring(getFileName().length() - 4);

    }

    @Override
    public boolean isEmpty() {

        if (getFileName().length() == 0) {
            return true;
        } else {

            File newFile = new File(getFileName());
            if (newFile.length() >= 0) {
                return false;
            } else {
                return true;
            }

        }
    }

    @Override
    public long getSize() {
        Long size = 0L;
        if (!this.isEmpty()) {
            File newFile = new File(getFileName());
            try {
                size = newFile.length();
            } catch (SecurityException ex) {
//                return HttpStatus.UNPROCESSABLE_ENTITY;
            } catch (Exception ex) {

            }
        }
        return size;
    }

    @Override
    public byte[] getBytes() throws IOException {
        byte[] size = new byte[0];
//        FileInputStream fin = null;
//
//        if (!this.isEmpty()) {
//            File newFile = new File(getFileName());
//            try {
//                // create FileInputStream object
//                fin = new FileInputStream(newFile);
//                size = fin.readAllBytes();
//            } catch (SecurityException ex) {
//            } catch (Exception ex) {
//
//            }
//            finally {
//                if(fin != null) {
//                    fin.close();
//                }
//            }
//        }

        try {
            size = this.getInputStream().readAllBytes();
        } catch (Exception ex) {
        }


        return size;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        FileInputStream uploadedFileInputStream = null;

        if (!this.isEmpty()) {
            File newFile = new File(getFileName());
            try {
                // create FileInputStream object
                uploadedFileInputStream = new FileInputStream(newFile);

            } catch (SecurityException ex) {
            } catch (Exception ex) {
            }



            //        return MyMultipartFile.getInputStream();
        }
        return uploadedFileInputStream;
    }


    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        OutputStream os = null;

        try {
            // Initialize a pointer
            // in file using OutputStream
            os = new FileOutputStream(dest);


            // Starts writing the bytes in it
            os.write(this.getBytes());
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        finally {
        if(os != null) os.close();
        }
    }

        //        MyMultipartFile.transferTo(dest);





    /*public static void main(String[] args) {

        File file = new File("inputfile.txt");
        FileInputStream fin = null;
        try {
            // create FileInputStream object
            fin = new FileInputStream(file);

            byte fileContent[] = new byte[(int)file.length()];

            // Reads up to certain bytes of data from this input stream into an array of bytes.
            fin.read(fileContent);
            //create string from byte array
            String s = new String(fileContent);
            System.out.println("File content: " + s);
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found" + e);
        }
        catch (IOException ioe) {
            System.out.println("Exception while reading file " + ioe);
        }
        finally {
            // close the streams using close method
            try {
                if (fin != null) {
                    fin.close();
                }
            }
            catch (IOException ioe) {
                System.out.println("Error while closing stream: " + ioe);
            }
        }
    }*/


}
