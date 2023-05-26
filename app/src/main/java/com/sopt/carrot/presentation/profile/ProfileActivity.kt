package com.sopt.carrot.presentation.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.sopt.carrot.R
import com.sopt.carrot.databinding.ActivityProfileBinding
import com.sopt.carrot.presentation.home.HomeActivity
import com.sopt.carrot.util.toast

class ProfileActivity : AppCompatActivity() {

    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var binding: ActivityProfileBinding

    private val getImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri = result.data?.data
                binding.ivProfileImgLoad.setImageURI(imageUri)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this // lifecycleOwner 설정

        with(binding) {
            // 초기값
            btnProfileWoman.isSelected = true
            btnProfileWoman.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
            btnProfileAgreeAndApply.isEnabled = false
        }
        setupClickListeners()
        observe()
    }

    private fun observe() {
        // introductionTextCount 업데이트
        viewModel.introductionTextCount.observe(this, { textCount ->
            binding.tvProfileIntroductionTextCount.text = textCount
        })

        // isButtonEnabled 업데이트
        viewModel.isButtonEnabled.observe(this, { isEnabled ->
            binding.btnProfileAgreeAndApply.isEnabled = isEnabled
        })

        // 통신성공시 뷰 전환
        viewModel.applyResult.observe(this) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        // 통신실패시 토스트 메세지
        viewModel.errorResult.observe(this) { errorResult ->
            this.toast(errorResult.message)
        }
    }

    fun setupClickListeners() {
        with(binding) {
            // ivProfileImgLoad 터치 시 갤러리 열기
            ivProfileImgLoad.setOnClickListener {
                openGallery()
            }
            btnProfileMan.setOnClickListener {
                selectGender(true)
            }

            btnProfileWoman.setOnClickListener {
                selectGender(false)
            }

            ivProfileStopProfile.setOnClickListener {
                val dialog = CustomPopupDialog(this@ProfileActivity)
                dialog.show()
            }
            btnProfileAgreeAndApply.setOnClickListener {
                viewModel?.applyToServer()
            }
        }
    }

    private fun selectGender(isManSelected: Boolean) {
        with(binding) {
            btnProfileMan.isSelected = isManSelected
            btnProfileWoman.isSelected = !isManSelected

            btnProfileMan.setTextColor(
                ContextCompat.getColor(
                    applicationContext,
                    if (isManSelected) R.color.white else R.color.color_sub_gray3
                )
            )
            btnProfileWoman.setTextColor(
                ContextCompat.getColor(
                    applicationContext,
                    if (isManSelected) R.color.color_sub_gray3 else R.color.white
                )
            )
        }
        viewModel?.selectedGender?.value = if (isManSelected) 0 else 1
    }

    // 갤러리 열기
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        getImage.launch(intent)
    }
}
