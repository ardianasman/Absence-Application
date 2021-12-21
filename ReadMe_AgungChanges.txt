Mencoba commit pertama

3/12
- tambah permission camera dan internet di manifest
- ganti minSdk di build.gradle ke 21 supaya bisa dipakai lebih banyak hp
- tambah lib untuk scan qr code
- ganti warna theme sesuai pallete
- layout untuk scan, item list karyawan dan input karyawan

21/12
- QR code di akun pengurus berisi tanggal hari ini, jadi tiap hari qr codenya ganti
- saat scan qr code dicek apakah tanggalnya sesuai, jadi gak bisa pakai qr code kemarin
- karyawan berisi nama, email, telp, role, pic (gambar) -> tolong ini jangan dihapus di firestore, password
- gambar disimpan secara local jadi kalau login di tempat lain gambar tidak akan keluar
- bottom nav di home pengurus secara default tidak keluar labelnya. Kalau diklik baru keluar. Ini karena menunya ada 4