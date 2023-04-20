package com.example.baitest2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.baitest2.databinding.ActivityProductDetailsBinding
import com.google.firebase.database.FirebaseDatabase

private lateinit var binding: ActivityProductDetailsBinding
class ProductDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setValueToView()
        // code cho nút delete
        binding.btnDelete.setOnClickListener {
            deteleRecord(
                intent.getStringExtra("prdId").toString()
            )
        }
    }


    private fun deteleRecord(id: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("products").child(id)
        val mTask = dbRef.removeValue()
        mTask.addOnSuccessListener {
            Toast.makeText(this, "xóa thành công", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ err ->
            Toast.makeText(this, "không thành công ${err.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setValueToView() {
        binding.tvPrdId.text = intent.getStringExtra("prdId")
        binding.tvPrdName.text = intent.getStringExtra("prdName")
        binding.tvPrdType.text = intent.getStringExtra("prdType")
        binding.tvPrdPrice.text = intent.getStringExtra("prdPrice")
    }
}