package com.example.pagging.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.pagging.databinding.FragmentDetailsBinding
import com.example.pagging.ui.movie.MovieViewModel
import com.example.pagging.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {


    lateinit var binding: FragmentDetailsBinding

    private val args: DetailsFragmentArgs by navArgs()

    val viewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        binding.backPress.setOnClickListener {
            findNavController().popBackStack()
        }
        Glide.with(this)
            .load("https://img.omdbapi.com/?i=${args.imdbId}&apikey=50bd3d3a")
            .into(binding.posterDetail)

        viewModel.getMovieDetails(args.imdbId!!)

        viewModel.movieDetails.observe(viewLifecycleOwner) {
            when (it.getContentIfNotHandled()?.status) {

                Status.LOADING -> {
                    binding.detailsProgress.visibility = View.VISIBLE
                }

                Status.ERROR -> {
                    binding.detailsProgress.visibility = View.GONE
                }

                Status.SUCCESS -> {
                    binding.detailsProgress.visibility = View.GONE
                    binding.movieDetails = it.peekContent().data
                }

                null -> {
                    binding.detailsProgress.visibility = View.VISIBLE
                }
            }
        }
    }
}