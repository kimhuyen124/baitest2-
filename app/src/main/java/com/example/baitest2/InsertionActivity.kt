package com.example.baitest2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {
    private lateinit var dbRef : DatabaseReference
    private lateinit var btnSave : Button
    private lateinit var edtPrdName : EditText
    private lateinit var edtPrdType : EditText
    private lateinit var edtPrdPrice : EditText
    private lateinit var edtPrdImg : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        btnSave = findViewById(R.id.btnSave)
        edtPrdName = findViewById(R.id.edtPrdName)
        edtPrdType = findViewById(R.id.edtPrdType)
        edtPrdPrice = findViewById(R.id.edtPrdPrice)
        edtPrdImg = findViewById(R.id.edtPrdImg)
        dbRef = FirebaseDatabase.getInstance().getReference("products")
        // xử lí sự kiện khi click vòa nút save
        btnSave.setOnClickListener {
            saveProductData()
        }

    }

    private fun saveProductData() {
// lấy dữ liệu
        val prdName = edtPrdName.text.toString()
        val prdType = edtPrdType.text.toString()
        val prdPrice = edtPrdPrice.text.toString()
        val prdImg = edtPrdImg.text.toString()
        // đẩy dữ liệu
        val prdId = dbRef.push().key!!
        val product = ProductModel(prdId, prdName, prdType, prdPrice, prdImg )
        // kiểm tra điều kiện
        if (prdName.isEmpty() || prdType.isEmpty() || prdPrice.isEmpty() || prdImg.isEmpty() ) {
            Toast.makeText(this, "Không thành công", Toast.LENGTH_SHORT).show()
            return
        }

        dbRef.child(prdId).setValue(product)
            .addOnCompleteListener{
                Toast.makeText(this, "thành công", Toast.LENGTH_SHORT).show()
                edtPrdName.setText("")
                edtPrdType.setText("")
                edtPrdPrice.setText("")
                edtPrdImg.setText("")
                val intent = Intent(this,FetchingActivity::class.java)
                startActivity(intent)
            }
            .addOnFailureListener{err->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()

            }
    }
}