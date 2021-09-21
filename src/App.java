
import java.io.IOException;
import java.util.Scanner;

import CRUD.Aldi;
import CRUD.Utility;



public class App {
    public static void main(String[] args) throws IOException {
     
        Scanner terminalInput = new Scanner(System.in);
        String pilihanUser;
        boolean islanjutkan = true;


        while(islanjutkan){
        Utility.clearScreen();
        System.out.println("Database Perpustakaan\n");
        System.out.println("1.\tLihat seluruh buku");
        System.out.println("2.\tCari data buku");   
        System.out.println("3.\tTambah data buku");
        System.out.println("4.\tUbah data buku");
        System.out.println("5.\tHapus data buku");

        System.out.print("\n\nPilihan anda: ");
        pilihanUser = terminalInput.next();

        switch (pilihanUser){
            case "1":
                System.out.println("\n=================");
                System.out.println("LIST SELURUH BUKU");
                System.out.println("=================");
                Aldi.tamlpilkanData();
                break;
            case "2":
                System.out.println("\n=========");
                System.out.println("CARI BUKU");
                System.out.println("=========");
                Aldi.CariData();
                break;
            case "3":
                System.out.println("\n===========");
                System.out.println("TAMBAH BUKU");
                System.out.println("===========");
                Aldi.tambahData();
                Aldi.tamlpilkanData();
                break;
            case "4":
                System.out.println("\n==============");
                System.out.println("UBAH DATA BUKU");
                System.out.println("==============");
                Aldi.updateData(); 
                break;
            case "5":
                System.out.println("\n===============");
                System.out.println("HAPUS DATA BUKU");
                System.out.println("===============");
                Aldi.deleteData();
                break;
            default:
                System.err.println("\nInput anda tidak ditemukan\nSilahkan pilih (1-5)");
        }
        

        islanjutkan = Utility.getYesorNO("Apakah anda ingin melanjutkan");
    }


    } 
}
