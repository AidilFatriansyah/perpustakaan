package CRUD;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Aldi {

    public static void tamlpilkanData() throws IOException{
        
        FileReader fileInput;
        BufferedReader bufferInput;

        try {
            fileInput = new FileReader("database.txt");
            bufferInput = new BufferedReader(fileInput);
        } catch (Exception e){
            System.err.println("Database tidak ditemukan");
            System.err.println("Silahkan tambah data terlebih dahulu");
            tambahData();
            return;
        }

        System.out.println("\n| No |\tTahun  |\tPenulis                |\tPenerbit               |\tJudul buku");
        System.out.println("======================================================================================================");


        String data = bufferInput.readLine();
        int nomorData = 0;
        while(data != null){
            nomorData++;
            StringTokenizer stringToken = new StringTokenizer(data, ",");

            stringToken.nextToken();
            System.out.printf("| %2d ",nomorData);
            System.out.printf("|\t%4s   ",stringToken.nextToken());
            System.out.printf("|\t%-20s   ",stringToken.nextToken());
            System.out.printf("|\t%-20s   ",stringToken.nextToken());
            System.out.printf("|\t%s   ",stringToken.nextToken());
            System.out.println("\n");

            data = bufferInput.readLine();
        }

        System.out.println("======================================================================================================");
        
    }

    public static void CariData() throws IOException{

        //membaca database ada atau tidak
        try {
            File file = new File("database.txt");
        } catch (Exception e){
            System.err.println("Database tidak ditemukan");
            System.err.println("Silahkan tambah data terlebih dahulu");
            tambahData();
            return;
        }

        //kita ambil keyword dari user
        Scanner terminalInput = new Scanner(System.in);
        System.out.print("Masukan kata kunci untuk mencari buku: ");
        String cariString = terminalInput.nextLine();
        String [] keywords = cariString.split("\\s+");

        //kita cek keyword di database
        Utility.cekBukuDiDatabase(keywords, true);

    }

    public static void  tambahData() throws IOException{

        FileWriter fileOutput = new FileWriter("database.txt", true);
        BufferedWriter bufferedOutput = new BufferedWriter(fileOutput);

        //mengambil input dari user
        Scanner terminalInput = new Scanner(System.in);
        String penulis, judul, penerbit, tahun;

        System.out.print("masukan nama penulis : ");
        penulis = terminalInput.nextLine();
        System.out.print("masukan judul buku : ");
        judul = terminalInput.nextLine();
        System.out.print("masukan nama penerbit : ");
        penerbit = terminalInput.nextLine();
        System.out.print("masukan tahun terbit, format = (YYYY) : ");
        tahun = Utility.ambilTahun();

        //cek buku di database
        
        String[] keywords = {tahun+","+penulis+","+penerbit+","+judul};
        System.out.println(Arrays.toString(keywords));

        boolean isExist = Utility.cekBukuDiDatabase(keywords, false);
        
        //menulis buku di database
        if(!isExist){
           // fiersabesari_2012_1,2012,fiersa besari,media kita,jejak langkah

           System.out.println(Utility.ambilEntryPerTahun(penulis, tahun));
           long nomorentry = Utility.ambilEntryPerTahun(penulis,tahun)+1;

           String penulisWithoutSpace = penulis.replaceAll("\\s+", "");
           String primaryKey = penulisWithoutSpace+"_"+tahun+"_"+nomorentry;
           System.out.println("\nData yang akan anda masukan adalah");
           System.out.println("------------------------------------");
           System.out.println("primaryKey       : " + primaryKey);
           System.out.println("tahun terbit     : " + tahun);
           System.out.println("penulis          : " + penulis);
           System.out.println("judul            : " + judul);
           System.out.println("penerbit         : " + penerbit);

           boolean isTambah = Utility.getYesorNO("apakah anda akan menambah data tersebut?");

            if(isTambah){
                bufferedOutput.write(primaryKey + "," + tahun + "," + penulis + "," + penerbit + "," + judul);
                bufferedOutput.newLine();
                bufferedOutput.flush();
            }

        }else{
            System.out.println("buku yang anda masukan sudah ada tersedia di database dengan data berikut :");
            Utility.cekBukuDiDatabase(keywords, true);
        }

        

        bufferedOutput.close();
    }

    public static void updateData() throws IOException{

        //kita ambil database original
        File database = new File("database.txt");
        FileReader fileInput = new FileReader(database);
        BufferedReader bufferedInput = new BufferedReader(fileInput);

        //kita buat database sementara
        File tempDB = new File("tempDB.txt");
        FileWriter fileOutput = new FileWriter(tempDB);
        BufferedWriter bufferedWriter = new BufferedWriter(fileOutput);

        //tampilkan data
        System.out.println("LIST BUKU");
        tamlpilkanData();

        //ambil user input / pilihan data
        Scanner terminalInput = new Scanner(System.in);
        System.out.print("\nMasukan nomor buku yang akan diupdate : ");
        int updateNum = terminalInput.nextInt();

        //tampilkan data yang ingin di update
        String data = bufferedInput.readLine();
        int entryCount = 0;

        while(data != null){
            entryCount++;

            StringTokenizer st = new StringTokenizer(data, ",");

            //tampilkan entryData == updateNum
            if(entryCount == updateNum){
                System.out.println("\nData yang ingin diupdate adalah : ");
                System.out.println("----------------------------------");
                System.out.println("Reference      : " + st.nextToken());
                System.out.println("Tahun          : " + st.nextToken());
                System.out.println("Penulis        : " + st.nextToken());
                System.out.println("Penerbit       : " + st.nextToken());
                System.out.println("Judul          : " + st.nextToken());

                //update data

                //mengambil input dari user
                String [] fieldData = {"tahun ", "penulis ", "penerbit ", "judul "};
                String [] tempData = new String[4];

                //refresh token
                st = new StringTokenizer(data, ",");
                String originalData = st.nextToken();

                for (int i = 0; i < fieldData.length; i++) {
                    boolean isUpdate = Utility.getYesorNO("apakah anda akan mengubah " + fieldData[i]);

                    originalData = st.nextToken();
                    if(isUpdate){
                        //user input

                        if (fieldData[i].equalsIgnoreCase("tahun")) {
                            
                            System.out.println("masukan tahun terbit, format=(YYYY): ");
                            tempData[i] = Utility.ambilTahun();

                        } else {
                            terminalInput = new Scanner(System.in);
                            System.out.print("\nMasukan " + fieldData[i] + " baru : ");
                            tempData[i] = terminalInput.nextLine();
                        }    
                    } else {
                        tempData[i] = originalData;
                    }
                }

                //tampilkan data baru kelayar
                st = new StringTokenizer(data, ",");
                st.nextToken();

                System.out.println("\nData baru anda adalah : ");
                System.out.println("----------------------------------");
                System.out.println("Tahun          : " + st.nextToken() + " --> " + tempData[0]);
                System.out.println("Penulis        : " + st.nextToken() + " --> " + tempData[1]);
                System.out.println("Penerbit       : " + st.nextToken() + " --> " + tempData[2]);
                System.out.println("Judul          : " + st.nextToken() + " --> " + tempData[3]);



                boolean isUpdate = Utility.getYesorNO("apakah anda yakin ingin mengupdate data tersebut");

                if(isUpdate){

                    //cek data baru di database
                    boolean isExist  = Utility.cekBukuDiDatabase(tempData, false);

                    if (isExist){

                        System.err.println("data buku sudah ada didatabase, \nsilahkan delete data yang bersangkutan");
                        bufferedWriter.write(data);

                    }else{

                        //format data baru kedalam database
                        String tahun = tempData[0];
                        String penulis = tempData[1];
                        String penerbit = tempData[2];
                        String judul = tempData[3];


                        //kita bikin primary key
                        long nomorentry = Utility.ambilEntryPerTahun(penulis,tahun)+1;

                        String penulisWithoutSpace = penulis.replaceAll("\\s+", "");
                        String primaryKey = penulisWithoutSpace+"_"+tahun+"_"+nomorentry;

                        //tulis data ke database
                        bufferedWriter.write(primaryKey + "," + tahun + "," + penulis + "," + penerbit + "," + judul);      
                    }
                } else {
                    //copy data
                    bufferedWriter.write(data);
                }
            } else {
                //copy data
                bufferedWriter.write(data);
            }

            bufferedWriter.newLine();

            data = bufferedInput.readLine();

        }

        //menulis data ke file
        bufferedWriter.flush();
        bufferedWriter.close();
        fileOutput.close();
        bufferedInput.close();
        fileInput.close();

        System.gc();
        //delete original file
        database.delete();
        //rename file sementara ke database
        tempDB.renameTo(database); 

    }

    public static void deleteData() throws IOException{
        //kita ambil database original
        File database = new File("database.txt");
        FileReader fileInput = new FileReader(database);
        BufferedReader bufferedInput = new BufferedReader(fileInput);

        //kita buat database sementara
        File tempDB = new File("tempDB.txt");
        FileWriter fileOutput = new FileWriter(tempDB);
        BufferedWriter bufferedOutput = new BufferedWriter(fileOutput);

        //tampilkan data
        System.out.println("List Buku");
        tamlpilkanData();

        //kita ambil user input untuk mendelete data
        Scanner terminalInput = new Scanner(System.in);
        System.out.print("\nMasukan nomor buku yang akan dihapus : ");
        int deleteNum = terminalInput.nextInt();

        //looping untuk membaca tiap data baris dan skip data yang akan di delete
        
        boolean isFound = false;
        int entryCounts = 0;

        String data = bufferedInput.readLine();

        while (data != null){
            entryCounts++;
            boolean isDelete = false;

            StringTokenizer st = new StringTokenizer(data , ",");
            //tampilkan data yang ingin di hapus
            if(deleteNum == entryCounts){
                System.out.println("\nData yang ingin anda hapus adalah : ");
                System.out.println("------------------------------------");
                System.out.println("Referensi        : " + st.nextToken());
                System.out.println("tahun            : " + st.nextToken());
                System.out.println("Penulis          : " + st.nextToken());
                System.out.println("Penerbit         : " + st.nextToken());
                System.out.println("Judul            : " + st.nextToken());

                isDelete = Utility.getYesorNO("apakah anda yakin akan menghapus? ");
                isFound = true;
            }

            if(isDelete){
                //skip pindahkan data dari original ke sementara
                System.out.println("data berhasil di hapus");
            } else {
                //kita pindahkan data dari original ke sementara
                bufferedOutput.write(data);
                bufferedOutput.newLine();
            }

            data = bufferedInput.readLine();

        }

        if (!isFound){
            System.err.println("buku tidak ditemukan!");
        }

        //menulis data ke file
        bufferedOutput.flush();
        bufferedOutput.close();
        fileOutput.close();
        bufferedInput.close();
        fileInput.close();

        System.gc();
        //delete original file
        database.delete();
        //rename file sementara ke database
        tempDB.renameTo(database);
        
    }
    
}