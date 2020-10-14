package com.test.mavericstest.ui.movielist

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.test.mavericstest.R
import com.test.mavericstest.common.Loading
import com.test.mavericstest.common.NetworkError
import com.test.mavericstest.common.Success
import com.test.mavericstest.common.ViewState
import com.test.mavericstest.data.models.Search
import com.test.mavericstest.ui.movielist.viewmodel.MovieFetcherViewModel
import com.test.mavericstest.util.CustomProgressDialog
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.android.synthetic.main.layout_movie_detail_footer.*
import kotlinx.android.synthetic.main.layout_movie_detail_header.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 * Use the [MovieDetails.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieDetails : Fragment() {


    private lateinit var viewModel: MovieFetcherViewModel
    private val progressDialog = CustomProgressDialog()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mSearch: Search

    private lateinit var mActivityContext: Context

    private var mImDbId = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mImDbId = it.getString(MovieDetails.Imdb_Id).toString()
        }
    }


    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        mActivityContext = context
        viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory)
            .get(MovieFetcherViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getSelectedMovieDetail(mImDbId)
        viewModel.getMovieDetails().observe(viewLifecycleOwner, Observer { onMovieDetails(it) })
    }

    private fun onMovieDetails(viewState: ViewState<com.test.mavericstest.data.models.MovieDetails>?) {
        when (viewState) {
            is Loading -> setProgress(true)
            is NetworkError -> {
                setProgress(false)
                showError(viewState.message!!)
            }
            is Success -> {
                setProgress(false)
                onMoviesDetails(viewState.mData)
            }
        }

    }

    private fun onMoviesDetails(mData: com.test.mavericstest.data.models.MovieDetails) {
        Glide.with(mActivityContext)
            .load(mData.poster)
            .centerInside()
            .into(movie_detail_poster)
        detail_header_title.text = mData.title
        detail_header_release.text = getString(R.string.release_date) + mData.released
        detail_header_category.text = getString(R.string.category) + mData.genre
        detail_header_runtime.text = getString(R.string.runtime) + mData.runtime
        detail_header_rating.text = getString(R.string.runtime) + mData.rated

        synopsisData.text = mData.plot
        detail_header_score.text = getString(R.string.score) + mData.imdbRating
        detail_header_review.text = getString(R.string.review) + mData.imdbVotes
        detail_header_popularity.text = getString(R.string.popularity) + mData.metascore

        detail_footer_director.text = getString(R.string.director) + mData.director
        detail_footer_writer.text = getString(R.string.writer) + mData.writer
        detail_footer_Actor.text = getString(R.string.actor) + mData.actors
    }

    private fun setProgress(isLoading: Boolean) {
        if (isLoading) {
            showProgress()
        } else {
            hideProgress()
        }
    }

    private fun showError(message: String) {
        if (isAdded)
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun showProgress() {
        if (isAdded) {
            progressDialog.show(requireContext(), getString(R.string.loading))
        }
    }

    private fun hideProgress() {
        if (isAdded)
            progressDialog.dialog.dismiss()
    }


    companion object {
        private val Imdb_Id: String = "IMDB"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment MovieDetails.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(iMdbId: String) = MovieDetails().apply {
            arguments = Bundle().apply {
                putString(Imdb_Id, iMdbId)
            }
        }

    }
}