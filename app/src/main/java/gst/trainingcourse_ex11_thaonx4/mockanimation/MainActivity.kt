package gst.trainingcourse_ex11_thaonx4.mockanimation

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import gst.trainingcourse_ex11_thaonx4.mockanimation.adapter.ItemAdapter
import gst.trainingcourse_ex11_thaonx4.mockanimation.adapter.RulerAdapter
import gst.trainingcourse_ex11_thaonx4.mockanimation.databinding.ActivityMainBinding
import gst.trainingcourse_ex11_thaonx4.mockanimation.model.DataProvider
import gst.trainingcourse_ex11_thaonx4.mockanimation.model.Item
import gst.trainingcourse_ex11_thaonx4.mockanimation.utils.Constants
import gst.trainingcourse_ex11_thaonx4.mockanimation.viewmodel.BudgetViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var itemAdapter: ItemAdapter
    private lateinit var rulerAdapter: RulerAdapter

    private var listValue = arrayListOf<Int>()

    private var lowPrice = 0
    private var normalPrice = 0
    private var highPrice = 0
    private var currentPrice = 0

    private var linearLayoutManager: LinearLayoutManager? = null

    private lateinit var animation: Animation
    private lateinit var viewMarkerAnimation: Animation

    private var items = arrayListOf<Item>()

    private val budgetViewModel: BudgetViewModel by lazy {
        ViewModelProvider(
            this,
            BudgetViewModel.BudgetViewModelFactory(this.application)
        )[BudgetViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getTextNotify(Constants.NORMAL, Constants.NORMAL_DESCRIPTION)

        budgetViewModel.insertBudget(Item(0,R.drawable.ic_cafe,"Cafe","300"))
        budgetViewModel.insertBudget(Item(1,R.drawable.ic_gym,"Gym","400"))
        budgetViewModel.insertBudget(Item(2,R.drawable.ic_taxi,"Taxi","700"))
        budgetViewModel.insertBudget(Item(3,R.drawable.ic_house,"House","200"))

        budgetViewModel.getAll().observe(this,{

            items.clear()
            items = it as ArrayList<Item>

            var averageBudget = 0

            for (item in items) {
                averageBudget += item.price.toInt()
            }
            binding.tvTotal.text = averageBudget.toString()

            lowPrice = averageBudget / items.size
            normalPrice = (averageBudget / (items.size)) * 2
            highPrice = (averageBudget / (items.size)) * 3

            setupViewPager()
            updatePrice()
        })


        setupRecyclerviewRuler()
    }

    private fun setupViewPager() {
        itemAdapter = ItemAdapter()
        binding.vp2.adapter = itemAdapter

       itemAdapter.differ.submitList(items)

        binding.vp2.apply {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

            val compositePageTransformer = CompositePageTransformer()

            compositePageTransformer.addTransformer(MarginPageTransformer(40))
            compositePageTransformer.addTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = 0.85f + r * 0.15f
                page.alpha = 0.5f

                if (position == 0f) {
                    page.alpha = 1f
                }
            }
            binding.vp2.setPageTransformer(compositePageTransformer)
        }
    }

    private fun updatePrice() {
        binding.vp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                val priceStart: Int = binding.tvPrice.text.toString().toInt()
                val priceEnd: Int = items[position].price.toInt()

                startCountAnimation(priceStart, priceEnd)

                when(position){
                    0 -> binding.btnSave.setOnClickListener {
                        val newPrice = binding.tvPrice.text.toString()
                        val item = Item(0,R.drawable.ic_cafe,"Cafe",newPrice)
                        budgetViewModel.updateBudget(item)
                    }

                    1 -> binding.btnSave.setOnClickListener {
                        val newPrice = binding.tvPrice.text.toString()
                        val item = Item(1,R.drawable.ic_gym,"Gym",newPrice)
                        budgetViewModel.updateBudget(item)
                    }

                    2 -> binding.btnSave.setOnClickListener {
                        val newPrice = binding.tvPrice.text.toString()
                        val item = Item(2,R.drawable.ic_taxi,"Taxi",newPrice)
                        budgetViewModel.updateBudget(item)
                    }

                    3 -> binding.btnSave.setOnClickListener {
                        val newPrice = binding.tvPrice.text.toString()
                        val item = Item(3,R.drawable.ic_house,"House",newPrice)
                        budgetViewModel.updateBudget(item)
                    }
                }
            }
        })
    }

    private fun startCountAnimation(priceStart: Int, priceEnd: Int) {
        val animator = ValueAnimator.ofInt(priceStart, priceEnd)
        animator.duration = 500
        animator.addUpdateListener { animation ->
            binding.tvPrice.text = animation.animatedValue.toString()
        }
        animator.start()
    }

    private fun setupRecyclerviewRuler() {

        rulerAdapter = RulerAdapter()
        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        for (n in 0..3000 step 10) {
            listValue.add(n)
        }

        binding.rvRuler.apply {
            layoutManager = linearLayoutManager
            adapter = rulerAdapter
        }
        rulerAdapter.differ.submitList(listValue)

        setOnScrollListener()
    }

    private fun setOnScrollListener() {

        binding.rvRuler.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                animation =
                    AnimationUtils.loadAnimation(applicationContext, R.anim.slide_down)
                binding.tvNotifyTitle.startAnimation(animation)
                binding.tvNotifyDescription.startAnimation(animation)

                viewMarkerAnimation =
                    AnimationUtils.loadAnimation(applicationContext, R.anim.zoom_in)
                binding.viewMarkerValue.startAnimation(viewMarkerAnimation)

                //vi tri dau tien con nhin thay tren man hinh khi keo recyclerview
                val visiblePosition = linearLayoutManager!!.findFirstVisibleItemPosition()
                val firstPrice: Int = visiblePosition.plus(40) * 10

                Handler(Looper.getMainLooper()).postDelayed({
                    currentPrice = firstPrice
                }, 1)

                startCountAnimation(currentPrice, firstPrice)

                when (firstPrice) {
                    in lowPrice until normalPrice -> {
                        getTextNotify(Constants.NORMAL, Constants.NORMAL_DESCRIPTION)
                    }
                    in normalPrice until highPrice -> {
                        getTextNotify(Constants.A_LOT, Constants.A_LOT_DESCRIPTION)
                    }
                    else -> {
                        getTextNotify(Constants.CRAZY, Constants.CRAZY_DESCRIPTION)
                    }
                }
            }
        })
    }

    private fun getTextNotify(title: String, description: String) {
        binding.tvNotifyTitle.text = title
        binding.tvNotifyDescription.text = description
    }
}