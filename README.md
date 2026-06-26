# Simple Tic-Tac-Toe Game with Java Swing, Login, and Statistics

## Student Information
Name : Alya Mutiara Putri

Student ID : 5026251021

Class : A

## Project Description
Small Projek ini merupakan projek pemrograman dasar yang berisi implementasi dari Java Swing, projek ini adalah games simple tic-tac-toe yang mana berisi berbagai fitur yang dapat dijalankan oleh pengguna, dalam games ini berisi fitur login, memulai permainan tic-tac-toe, statistik pemain, dan juga mencetak 5 skor pemain teratas.

## Features
- Login menggunakan database
- Play games tic-tac-toe menggunakan Swing GUI
- Mencetak win, lose, ataupun draw
- Menambahkan sound win, lose, draw saat permainan
- Menambahkan sound saat pemain meletakkan X atau O
- Menambahkan warna pada setiap frame
- Menambahkan warna terang saat pemain berhasil menciptakan kemenangan
- Mencetak statistik pemain
- Menampilkan top 5 score pemain

## Database
Database used: MySQL

## How To Run
- Buat database di mySQL
- Import schema.sql
- Buka Java project
- Tambahkan JDBC driver
- Tambahkan beberapa kelas yang dibutuhkan seperti : Databasemanager, GameFrame, GameLogic, LoginFrame, Main, MainMenuFrame, Player, PlayerService, StatistucsFrame, TopScorersFrame, dan terakhir kelas tambahan yaitu SoundManager
- Konfigurasi DataBaseManager.Java
- Jalankan Main.Java

## Class Explanation
- Main: Pusat program saat akan dijalankan dan merupakan kelas yang digunakan untuk mengecek koneksi ke database lalu membuka LoginFrame saat program dijalankan
- DatabaseManager: Mengelola koneksi JDBC ke MySQL dan menyediakan method getConnection() yang dipanggil setiap kali program butuh akses ke database
- Player: Menjadi model data pemain yang menyimpan informasi dari tabel database seperti id, username, wins, losses, draws, dan score
- PlayerService: Menjadi titik dari semua operasi database yang berkaitan dengan pemain, yaitu login() untuk cek username dan password, updateStatistics() untuk memperbarui data setelah game selesai, getPlayerById() untuk mengambil data terbaru, dan getTopFiveScorers() untuk mengambil 5 pemain teratas
- GameLogic: Berfungsi untuk menjelaskan aturaan permainan Tic-Tac-Toe, kelas ini berisi makeMove() untuk validasi dan eksekusi gerakan, checkWinner() untuk mengecek pemenang, isDraw() untuk mengecek seri, dan computerMove() untuk memilih langkah yang akan dijalankan oleh komputer dengan mengisi kotak yang kosong secara acak
- LoginFrame: Merupakan jendela pertama yang muncul saat aplikasi dibuka yang nantinya menerima input username dan password, memanggil PlayerService.login(), lalu membuka MainMenuFrame jika berhasil atau menampilkan pesan error jika gagal, dan berisikan UI untuk tampilan Login
- MainMenuFrame: Merupakan jendela menu utama setelah login berhasil, jendela ini menampilkan salam sambutan dan score pemain, serta tombol navigasi ke GameFrame, StatisticsFrame, TopScorersFrame, dan opsi keluar
- GameFrame: Jendela permainan utama berisi papan 3×3, yang akan menghubungkan tombol papan dengan GameLogic, menggambar simbol X dan O secara custom, mendeteksi hasil menang/kalah/seri, lalu memanggil PlayerService.updateStatistics() setelah game selesai
- StatisticsFrame: Jendela yang menampilkan statistik pribadi pemain yang sedang login, jendela ini mengambil data terbaru dari database menggunakan getPlayerById() dan menampilkan wins, losses, draws, score, dan win rate
- TopScorersFrame: Jendela yang menampilkan 5 pemain dengan score tertinggi dari database menggunakan JTable, pada jendela ini data diambil dari PlayerService.getTopFiveScorers() dan diurutkan berdasarkan score tertinggi, lalu wins terbanyak jika score sama

## Class Tambahan 
- SoundManager: Memainkan suara menggunakan javax.sound.sampled yang sudah built-in di Java, yang nantinya menghasilkan suara berbeda untuk menang, kalah, seri, dan klik tombol tanpa memerlukan file audio eksternal

## Screenshoot
<img width="433" height="314" alt="Screenshot 2026-06-26 120355" src="https://github.com/user-attachments/assets/885c3152-87fa-42df-bf1e-a1755d1dff57" />

<img width="433" height="315" alt="Screenshot 2026-06-26 120409" src="https://github.com/user-attachments/assets/a56f6a56-3beb-4b2f-94a4-5272ff342d65" />

<img width="428" height="316" alt="Screenshot 2026-06-26 120419" src="https://github.com/user-attachments/assets/f26e10e0-8dbc-4320-b91a-3821c254363c" />

<img width="480" height="580" alt="Screenshot 2026-06-26 120429" src="https://github.com/user-attachments/assets/63a5a509-a2c2-4442-b9ff-11561d243b31" />

<img width="549" height="688" alt="Screenshot 2026-06-26 120439" src="https://github.com/user-attachments/assets/2a85dc3d-3d8e-435a-8cff-d5f3ff758b7e" />

<img width="555" height="692" alt="Screenshot 2026-06-26 120452" src="https://github.com/user-attachments/assets/910478ab-f0cd-4cd6-83a5-ea0f5e5cd437" />

<img width="553" height="690" alt="Screenshot 2026-06-26 120532" src="https://github.com/user-attachments/assets/b49a23dc-9c3b-4ecd-90cf-79de0d3170e2" />

<img width="558" height="692" alt="Screenshot 2026-06-26 120750" src="https://github.com/user-attachments/assets/ee7a47ca-7992-4668-8b1f-d78afba51b39" />

<img width="555" height="691" alt="Screenshot 2026-06-26 120656" src="https://github.com/user-attachments/assets/de64b155-19e3-4f9a-b243-b5b15617df08" />

<img width="652" height="381" alt="Screenshot 2026-06-26 120708" src="https://github.com/user-attachments/assets/b9a0c224-8ae7-4e8e-9366-ffb72b32beae" />

<img width="423" height="512" alt="Screenshot 2026-06-26 120717" src="https://github.com/user-attachments/assets/7db46eee-63bc-4aca-9b93-e7d8cd4470f5" />

## Video Link
