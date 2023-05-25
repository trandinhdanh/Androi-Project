package com.example.shopgiay;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import Fragment.HomeFragment;
import ObjectClass.Product;
import ObjectClass.ProductDetails;
import database.DataBase;
import database.DataOfUser;

public class MainActivity extends AppCompatActivity {

    DataBase dataBase;
    SharedPreferences sharedPreferences;

//  mấy cái này dùng để insert hình ảnh khi chưa có trang admin

    Button select, previous, next;
    ImageView imageView, imgShow;
    int PICK_IMAGE_MULTIPLE = 1;
    EditText total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_them_hinh);
        dataBase = new DataBase(this, "shopgiay.sqlite", null, 1);
        sharedPreferences = getSharedPreferences("isLogin",MODE_PRIVATE);
        String idUserLogin = sharedPreferences.getString("idUser","");
        DataOfUser.idUser = idUserLogin;
        Toast.makeText(this,DataOfUser.idUser,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, MainFragActivity.class);
        startActivity(intent);


//  mấy cái này dùng để insert hình ảnh khi chưa có trang admin
        select = findViewById(R.id.select);
        total = findViewById(R.id.text);
        imageView = findViewById(R.id.image);
        previous = findViewById(R.id.previous);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_MULTIPLE);

            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
                byte[] img = byteArray.toByteArray();


                dataBase.insertImg(total.getText().toString().trim(),img );
                Toast.makeText(MainActivity.this, "Đã thêm", Toast.LENGTH_LONG).show();
            }
        });


       dataBase = new DataBase(this, "shopgiay.sqlite", null, 1);

        Toast.makeText(this, getDatabasePath("shopgiay.sqlite").getPath(), Toast.LENGTH_LONG).show();

//       Tạo bảnggggggggggggg
        //  isAdmin 0 là false, isLogin 0 là chưa đăng nhập
       dataBase.queryData("CREATE TABLE IF NOT EXISTS Account(userName VARCHAR(10) PRIMARY KEY, password VARCHAR(50), isAdmin INTEGER)");
//        table san pham
        dataBase.queryData("CREATE TABLE IF NOT EXISTS Product(idProd VARCHAR(10) PRIMARY KEY, nameProd VARCHAR(50), brand VARCHAR(20), price INTEGER, desc NVARCHAR(200))");
       //quantity là số lượng hàng nhập
//        dataBase.queryData("DROP TABLE ProductDetails");
        dataBase.queryData("CREATE TABLE IF NOT EXISTS ProductDetails(idProdDetails VARCHAR(10), idProd VARCHAR(10), size INTEGER, color VARCHAR(10), quantity INTEGER, PRIMARY KEY (idProdDetails, idProd, size, color))");
        dataBase.queryData("CREATE TABLE IF NOT EXISTS LinkImg_Prod(idImg INTEGER PRIMARY KEY AUTOINCREMENT,idProd VARCHAR(10), img BLOB) ");

//        table thoong tin khach hàng
        dataBase.queryData("CREATE TABLE IF NOT EXISTS Customer(userName VARCHAR(10),linkAvatar VARCHAR(100), nameCus VARCHAR(200), numberPhone VARCHAR(10), email VARCHAR(100), address NVARCHAR(100))");

//        table giỏ hang các sản phẩm lúc chưa đặt hàng
        dataBase.queryData("CREATE TABLE IF NOT EXISTS Cart(userName VARCHAR(10), idProdDetails VARCHAR(10) , amountCart INTEGER)");

//        table danh sách yêu thích
        dataBase.queryData("CREATE TABLE IF NOT EXISTS Favorite(userName VARCHAR(10), idProd VARCHAR(10),  PRIMARY KEY (userName, idProd))");

//        table đơn hàng có trạng thái đơn hàng state[ xac nhan(admin), huy don, da nhan(user)]

//        dataBase.queryData("DROP TABLE Orders");
        dataBase.queryData("CREATE TABLE IF NOT EXISTS Orders(idOrder VARCHAR(10),userName VARCHAR(10), idProdDetails VARCHAR(10) , amountOrder INTEGER, totalCost DOUBLE, delivery NVARCHAR(200), state VARCHAR(20))");

//
//        dataBase.queryData("INSERT INTO Account VALUES('truongdinh', '123', 0)");
//        dataBase.queryData("INSERT INTO Account VALUES('truonggiang', '123', 0)");
//        dataBase.queryData("INSERT INTO Account VALUES('hieudeptrai', '2704', 0)");
//        dataBase.queryData("INSERT INTO Account VALUES('admin', '123', 1)");
//
//        dataBase.queryData("INSERT INTO Product VALUES ('pd1','ADIDAS PREDATOR EDGE .1 TF CHAMPIONS CODE','Adidas','2950000','Nhằm tôn vinh sự hợp tác bền chặt giữa thương hiệu và giải đấu hấp dẫn nhất hành tinh, adidas đã tung ra BST Champions Code Pack vô cùng độc đáo vào tháng 3/2022 theo phong cách UEFA Champions League đặc trưng, với gam màu tím chủ đạo ở phần upper cùng các chi tiết màu bạc nổi bật lóe lên ở phần logo thương hiệu và hình ảnh các ngôi sao trên upper. Màu xanh dương chiếm chủ yếu ở phần cổ giày và phần gót sau. ')");
//        dataBase.queryData("INSERT INTO Product VALUES ('pd2','NIKE TIEMPO LEGEND 8 PRO TF PLAY MODE - CARDINAL','Nike','2590000','Nike Tiempo Legend 8 Pro TF Play Mode - Cardinal là mẫu giày dành cho sân cỏ nhân tạo 5-7 người. Tiempo 8 là phiên bản vừa được ra mắt vào tháng 6/2019 với nhiều cải tiến đáng kể. Đôi giày được làm bằng da thật nổi tiếng, được yêu thích bởi sự thoải mái và phong cách cổ điển.Tiempo Legend sở hữu màu đỏ đậm với lớp upper được thiết kế với cấu trúc 3D sáng tạo được làm từ da bê dưới dạng mặt cắt kim cương đem đến sự vừa vặn và cảm giác. Bên trong được trang bị dây đàn hồi dính liền với upper giúp tăng cường độ ôm chân.  ')");
//        dataBase.queryData("INSERT INTO Product VALUES ('pd3','NIKE MERCURIAL VAPOR 13 ACADEMY TF NJR JOGO PRISMATICO ','Nike','1690000','Giày đá banh Nike Mercurial Vapor 13 Academy TF NJR Jogo Prismatico - White/Black/Racer Blue/Volt là dòng sản phẩm dành cho sân cỏ nhân tạo 5-7 người. Tiếp nối thành công của người đàn anh Vapor 12, mùa hè 2019 NIKE đã cho ra mắt thế hệ thứ 13 của Mercurial Vapor, dòng sản phẩm bán chạy nhất của Nike Football.Không chỉ thay đổi về hình dáng bên ngoài, Nike Mercurial Vapor 13 còn có những cải tiến trong công nghệ. Thân giày Cấu tạo từ da tổng hợp được xử lý trở nên mềm và cảm giác hơn, Trọng lượng giày cũng được tối giản ở mức nhẹ nhất.')");
//        dataBase.queryData("INSERT INTO Product VALUES ('pd4','NIKE MERCURIAL SUPERFLYX 6 ACADEMY TF NJR MEU JOGO PACK','Nike','2150000','Nike Mercurial SuperflyX 6 Academy TF NJR Meu Jogo Pack - Amarillo/White/Black là mẫu giày chuyên dành cho sân cỏ nhân tạo 5 - 7 người. Đây là mẫu giày mới nhất NIKE ra mắt cho năm 2018, là bản lột xác của Nike Mercurial Victory VI, thay đổi hoàn toàn từ tên gọi đến chất liệu được cải tiến đến mức siêu mềm,siêu nhẹ, bạn sẽ phải bất ngờ khi cầm nó trên tay. SuperflyX là tên gọi của dòng sản phẩm Upper cổ cao ôm gọn chân đến mắt cá, Form giày thon và ôm gọn chân,Đinh TF gai cao su trải dài và đều khắp mặt đế,  phù hợp với các vị trí tấn công, sử dụng tốc độ và kỹ thuật, tiền vệ cánh, tiền đạo. ')");
//        dataBase.queryData("INSERT INTO Product VALUES ('pd5','MIZUNO MORELIA NEO III PRO TF - CYBER','Mizuno','3050000', 'Mizuno Morelia Neo III Pro TF - Cyber Yellow/Black là mẫu giày chuyên cho sân cỏ nhân tạo 5-7 người. Mẫu giày này được các cầu thủ đánh giá rất cao vì sự toàn diện, bền bỉ và hỗ trợ các cầu thủ rất tốt. Với những sự nâng cấp đáng chú ý, Morelia Neo III Pro AS hứa hẹn sẽ là mẫu giày hot không kém hai phiên bản tiền nhiệm. Những cầu thủ có lối chơi thiên về tốc độ và mong muốn một đôi giày nhẹ, êm ái, cảm giác bóng tốt không thể bỏ qua Morelia Neo III Pro AS.')");
//        dataBase.queryData("INSERT INTO Product VALUES ('pd6','MIZUNO MORELIA ULTRA LIGHT MADE IN JAPAN FG','Mizuno','5500000','Vào tháng 3 năm 2021, thương hiệu Nhật Bản đình đám Mizuno cho ra mắt một dòng giày đá bóng siêu nhẹ không hề kém cạnh Mercurial của Nike, X Ghosted của adidas hay Ultra của Puma. Sự ra mắt của Mizuno Morelia Ultra Light MIJ chính xác là một sự chứng minh Mizuno không hề kém cạnh cuộc trong cuộc đua tốc độ này.')");
//        dataBase.queryData("INSERT INTO Product VALUES ('pd7','GIÀY ĐÁ BÓNG KAMITO TA11 TF TOUCH OF MAGIC','Kamito','690000','KAMITO TA11 là một trong những mẫu giày đá bóng vô cùng nổi tiếng trong giới bóng đá phong trào, được thiết kế với sự góp ý của cầu thủ Tuấn Anh - Hoàng Anh Gia Lai Việt Nam. Kamito TA11 ra đời để vinh danh cũng như ghi nhận những đóng góp đáng giá của cầu thủ Nguyễn Tuấn Anh. Giống như những mẫu giày \"Signature\" khác, Kamito TA11 sở hữu những đặc trưng khác biệt và thú vị liên quan đến đội trưởng của CLB Hoàng Anh Gia Lai.')");
//        dataBase.queryData("INSERT INTO Product VALUES ('pd8','NIKE MERCURIAL VAPOR 14 ACADEMY TF THE PROGRESS','Nike','1890000','Trở lại “đường đua” một cách mới mẻ và thú vị hơn, Nike phát hành bộ sưu tập giày đá bóng ‘Progress Pack’ giàu ý nghĩa cùng vẻ ngoài tươi sáng. Cái tên ‘Progress Pack’ mà Nike chọn đặt cho Bộ sưu tập lần này mang nhiều ý nghĩa thú vị. Ngoài việc hy vọng các cầu thủ sẽ tiến bộ hơn trong các trận đấu sắp tới, Nike còn muốn khẳng định bóng đá và những cầu thủ sẽ luôn trong tiến trình (Work in Progress) không ngừng phát triển và hoàn thiện bản thân trở nên ngày càng tốt hơn. ')");
//        dataBase.queryData("INSERT INTO Product VALUES ('pd9','NIKE TIEMPO LEGEND 9 PRO TF RECHARGE','Nike','2590000','Những màu sắc giàu năng lượng đem đến động lực mới giúp các cầu thủ ra sân bất chấp thời tiết mùa đông khắc nghiệt ở châu Âu. Tiempo dù xuất hiện sau cùng nhưng vẫn sở hữu concept tương ứng như Mercurial Superfly 8, Vapor 14 và Phantom GT II trong đội hình Recharge.Nike Tiempo Legend 9 Pro TF Recharge - Grey Fog/Volt/Sapphire là phiên bản giày cao cấp chuyên cho sân cỏ nhân tạo 5-7 người. Phiên bản Tiempo Legend 9 sở hữu những gam màu tương tự như những người anh em còn lại trong đội hình Recharge với gam màu chủ đạo là xám “Grey Fog”, các chi tiết điểm nhấn màu xanh Volt ở logo Swoosh và màu xanh ngọc \"Sapphire\" phủ ở gót giày. Nếu bạn đang tìm kiếm một mẫu giày bóng đá sân cỏ nhân tạo toàn diện từ thiết kế cho tới các chức năng thì Nike Tiempo Legend 9 chính là mẫu giày dành cho bạn.')");
//
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details1', 'pd1', '40','Purple','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details2', 'pd1', '40','White','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details3', 'pd1', '40','Black','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details4', 'pd1', '41','Purple','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details5', 'pd1', '41','Red','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details6', 'pd1', '42','Purple','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details7', 'pd1', '40','Blue','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details8', 'pd1', '41','Pink','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details9', 'pd1', '42','Red','10')");
//
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details11', 'pd2', '40','Purple','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details12', 'pd2', '40','White','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details13', 'pd2', '40','Black','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details14', 'pd2', '41','Purple','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details15', 'pd2', '41','Red','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details16', 'pd2', '42','Purple','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details17', 'pd2', '40','Blue','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details18', 'pd2', '41','Pink','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details19', 'pd2', '42','Red','10')");
//
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details21', 'pd3', '40','Purple','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details22', 'pd3', '40','White','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details23', 'pd3', '40','Black','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details24', 'pd3', '41','Purple','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details25', 'pd3', '41','Red','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details26', 'pd3', '42','Purple','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details27', 'pd3', '40','Blue','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details28', 'pd3', '41','Pink','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details29', 'pd3', '42','Red','10')");
//
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details31', 'pd4', '40','Purple','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details32', 'pd4', '40','White','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details33', 'pd4', '40','Black','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details34', 'pd4', '41','Purple','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details35', 'pd4', '41','Red','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details36', 'pd4', '42','Purple','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details37', 'pd4', '40','Blue','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details38', 'pd4', '41','Pink','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details39', 'pd4', '42','Red','10')");
//
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details41', 'pd5', '40','Purple','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details42', 'pd5', '40','White','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details43', 'pd5', '40','Black','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details44', 'pd5', '41','Purple','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details45', 'pd5', '41','Red','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details46', 'pd5', '42','Purple','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details47', 'pd5', '40','Blue','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details48', 'pd5', '41','Pink','10')");
//        dataBase.queryData("INSERT INTO ProductDetails VALUES ('details49', 'pd5', '42','Red','10')");



//        dataBase.queryData("DELETE FROM LinkImg_Prod WHERE idprod = ''");


        Log.e("main", "done insert");
        dataBase.close();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
    }
    }
}