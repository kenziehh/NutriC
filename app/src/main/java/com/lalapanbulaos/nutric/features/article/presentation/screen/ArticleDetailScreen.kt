package com.lalapanbulaos.nutric.features.article.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lalapanbulaos.nutric.R
import com.lalapanbulaos.nutric.presentation.theme.Colors
import com.lalapanbulaos.nutric.presentation.theme.NutriCTypography

@Composable
fun ArticleDetailScreen(onGoBack: () -> Unit = {}) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .padding(vertical = 20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text("Kembali", style = NutriCTypography.subHeadingSm)
        }

        Image(
            contentDescription = "Article",
            painter = painterResource(R.drawable.article_dummy),
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
        )

        Column {
            Text("Menjaga Pola Makan Sehat untuk Hidup Lebih Bahagia",style=NutriCTypography.subHeadingMd)
            Spacer(modifier = Modifier.height(8.dp))
            Column {
                Text("Ditulis oleh RIZKY YUNIHARTO", style = NutriCTypography.bodySm, color = Color(0XFF848484))
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically){
                    Text("Tue 05/09/24", style = NutriCTypography.bodyXs, color = Colors.Secondary.color30)
                    Spacer(modifier = Modifier.width(4.dp))
                    Box(modifier = Modifier.size(2.dp).background(color = Colors.Neutral.color30, shape = CircleShape))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("17:09", style = NutriCTypography.bodyXs, color = Colors.Secondary.color30)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Pola makan yang sehat bukan hanya sekadar tren, tetapi merupakan kunci untuk menjaga kesehatan tubuh, meningkatkan energi, dan mendukung kebahagiaan sehari-hari. Pola makan yang seimbang dapat membantu mencegah berbagai penyakit, mengoptimalkan metabolisme, serta meningkatkan fokus dan produktivitas. Yuk, simak sembilan langkah berikut untuk memulai pola makan sehat yang berkelanjutan!\n" +
                "\n" +
                "1. Pilih Makanan yang Beragam dan Seimbang\n" +
                "Mengonsumsi berbagai jenis makanan memastikan tubuh mendapatkan beragam nutrisi. Kombinasikan makanan dengan kandungan karbohidrat, protein, lemak sehat, vitamin, dan mineral. Penuhi piring dengan sayuran berwarna, biji-bijian, dan sumber protein, baik dari hewani maupun nabati.\n" +
                "\n" +
                "2. Kurangi Asupan Gula dan Makanan Olahan\n" +
                "Gula tambahan dan makanan olahan sering kali rendah nutrisi tetapi tinggi kalori. Konsumsi gula berlebih dapat menyebabkan energi mudah turun, meningkatkan risiko diabetes, dan obesitas. Gantilah dengan makanan segar atau makanan utuh yang memiliki kandungan nutrisi alami lebih tinggi.\n" +
                "\n" +
                "3. Perbanyak Asupan Serat\n" +
                "Serat penting untuk pencernaan yang sehat dan memberikan rasa kenyang lebih lama. Serat juga dapat menurunkan kadar kolesterol dan membantu mengatur kadar gula darah. Dapatkan asupan serat dari buah-buahan, sayuran, kacang-kacangan, dan biji-bijian.\n" +
                "\n" +
                "4. Minum Air yang Cukup\n" +
                "Hidrasi adalah bagian penting dari pola makan sehat. Minum air yang cukup membantu proses metabolisme, menjaga kesehatan kulit, serta meningkatkan fokus dan energi. Usahakan minum minimal 8 gelas air per hari atau lebih sesuai kebutuhan tubuh.\n" +
                "\n" +
                "5. Atur Porsi Makan yang Sesuai\n" +
                "Mengatur porsi makan membantu menjaga asupan kalori dan mencegah makan berlebihan. Gunakan piring yang lebih kecil, dan mulailah makan dengan porsi sayuran yang lebih besar. Makanlah perlahan agar tubuh dapat merasakan kenyang sebelum mengonsumsi makanan berlebihan.\n" +
                "\n" +
                "6. Konsumsi Lemak Sehat\n" +
                "Lemak sehat penting untuk tubuh, terutama bagi kesehatan otak dan jantung. Pilih sumber lemak sehat seperti alpukat, kacang-kacangan, biji chia, minyak zaitun, dan ikan berlemak. Hindari lemak trans dan kurangi lemak jenuh agar jantung tetap sehat.\n" +
                "\n" +
                "7. Jangan Lewatkan Sarapan\n" +
                "Sarapan adalah bahan bakar penting untuk memulai hari dengan energi yang cukup. Pilih sarapan yang kaya protein, serat, dan lemak sehat untuk menjaga energi lebih stabil. Oatmeal, smoothie buah, atau roti gandum dengan telur adalah beberapa pilihan sarapan sehat yang bisa dicoba.\n" +
                "\n" +
                "8. Cermati Label Nutrisi\n" +
                "Membaca label nutrisi membantu Anda memilih produk yang lebih sehat dan terhindar dari bahan tambahan berlebihan. Fokus pada kandungan kalori, gula tambahan, lemak, dan garam dalam produk kemasan. Dengan begitu, Anda bisa mengontrol asupan nutrisi yang masuk ke tubuh.\n" +
                "\n" +
                "9. Pertimbangkan Konsumsi Multivitamin\n" +
                "Jika pola makan belum seimbang, konsumsi multivitamin bisa membantu melengkapi nutrisi yang kurang. Namun, multivitamin tidak bisa menggantikan nutrisi dari makanan alami, jadi sebaiknya tetap prioritaskan makanan segar dan alami.\n" +
                "\n" +
                "Kesimpulan\n" +
                "Mengatur pola makan sehat adalah investasi terbaik untuk kesehatan jangka panjang. Dengan melakukan langkah-langkah di atas, Anda bisa menjaga kebugaran tubuh, meningkatkan kebahagiaan, dan tetap berenergi sepanjang hari. Mulailah secara bertahap dan nikmati perubahan positif pada tubuh Anda!", style = NutriCTypography.bodySm, color = Color(0XFF848484))
    }
}
