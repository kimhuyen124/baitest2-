package com.example.baitest2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baitest2.Adapter.PrdAdapter
import com.example.baitest2.databinding.ActivityFetchingBinding
import com.google.firebase.database.*

private lateinit var binding: ActivityFetchingBinding
class FetchingActivity : AppCompatActivity(){

    private lateinit var ds:ArrayList<ProductModel>
    private lateinit var dbRef:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFetchingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvPrd.layoutManager = LinearLayoutManager(this)
        binding.rvPrd.setHasFixedSize(true)
        ds = arrayListOf<ProductModel>()
        GetThongTinSP()
    }

    private fun GetThongTinSP() {
        binding.rvPrd.visibility = View.GONE
        binding.txtLoadingData.visibility = View.VISIBLE
        dbRef = FirebaseDatabase.getInstance().getReference("products")
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                ds.clear()
                if (snapshot.exists()){
                    for(prdSnap in snapshot.children){
                        val prdData = prdSnap.getValue(ProductModel::class.java)
                        ds.add(prdData!!)
                    }
                    val mAdapter = PrdAdapter(ds)
                    binding.rvPrd.adapter = mAdapter
                    // code lắng nghe sự kiện lên item rv
                    mAdapter.setOnItemClickListener(object : PrdAdapter.onItemClickListener{
                        // ctrl + i tại đây
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@FetchingActivity,ProductDetailsActivity::class.java)
                            // put Extra
                            intent.putExtra("prdId",ds[position].prdID)
                            intent.putExtra("prdName",ds[position].prdName)
                            intent.putExtra("prdType",ds[position].prdType)
                            intent.putExtra("prdPrice",ds[position].prdPrice)
                            startActivity(intent)
                        }
                    })
                    binding.rvPrd.visibility = View.VISIBLE
                    binding.txtLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
}