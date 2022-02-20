package com.aliosmanarslan.foodproject.view.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.aliosmanarslan.foodproject.R
import com.aliosmanarslan.foodproject.recycler.adapter.RecyclerBasketFoodAdapter
import com.aliosmanarslan.foodproject.recycler.swipe.SwipeGesture
import com.aliosmanarslan.foodproject.databinding.FragmentFoodBasketBinding
import com.aliosmanarslan.foodproject.models.basketfoodmodel.BasketFoodModel
import com.aliosmanarslan.foodproject.viewmodel.FoodBasketFragmentViewModel

class FoodBasketFragment : Fragment() {

    private lateinit var binding : FragmentFoodBasketBinding
    private lateinit var viewModel : FoodBasketFragmentViewModel
    private lateinit var adapter : RecyclerBasketFoodAdapter
    private lateinit var finalBasketList : ArrayList<BasketFoodModel>

    private var subtotal : Int = 0
    private var tax : Float = 0f
    private var total : Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : FoodBasketFragmentViewModel by viewModels()
        viewModel = tempViewModel
        finalBasketList = ArrayList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_food_basket, container, false)
        binding.foodBasketFragment = this
        binding.subtotal = subtotal
        binding.tax = tax
        binding.total = total

        viewModel.backPressed(requireActivity(), binding.root)

        viewModel.foodBasketList.observe(viewLifecycleOwner,{ basketList ->
            binding.emptyRecycler.visibility = View.INVISIBLE
            viewModel.specialBasketList(finalBasketList, basketList)

            finalBasketList.forEach { foods ->
                subtotal += foods.food_price.toInt()
            }
            tax = subtotal * 0.08f
            total = subtotal + tax

            binding.subtotal = subtotal
            binding.tax = tax
            binding.total = total

            val swipeGesture = object : SwipeGesture(requireContext()){
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    when(direction){
                        ItemTouchHelper.LEFT -> {
                            viewModel.delete(viewHolder.itemView, finalBasketList[viewHolder.adapterPosition].food_id)
                        }
                    }
                }
            }
            val touchHelper = ItemTouchHelper(swipeGesture)
            touchHelper.attachToRecyclerView(binding.foodBasketRecyclerView)

            adapter = RecyclerBasketFoodAdapter(requireContext(), finalBasketList, viewModel)
            binding.basketFoodAdapter = adapter

        })

        return binding.root
    }

    fun moveFoodsFragment(v : View){
        viewModel.moveFoodsFragment(v)
    }

}