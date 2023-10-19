package dev.janssenbatista.githubsearch

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.isVisible
import dev.janssenbatista.githubsearch.adapters.RepositoryAdapter
import dev.janssenbatista.githubsearch.databinding.ActivityMainBinding
import dev.janssenbatista.githubsearch.dialogs.AboutDialog
import dev.janssenbatista.githubsearch.models.Repository
import dev.janssenbatista.githubsearch.repositories.Result
import dev.janssenbatista.githubsearch.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<MainViewModel> { MainViewModel.Factory }
    private val adapter by lazy {
        RepositoryAdapter(
            iconShareClickListener,
            openRepositoryClickListener
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.result.observe(this) { result ->
            setupResult(result)
        }
        binding.apply {
            btnSearch.setOnClickListener {
                viewModel.getAllRepositories(binding.tiUser.text.toString().trim())
            }
            rvRepositories.adapter = adapter
            ivAbout.setOnClickListener {
                openAboutDialog()
            }
        }
    }

    private fun setupResult(result: Result) {
        when (result) {
            is Result.Loading -> {
                binding.apply {
                    progressBar.isVisible = true
                    rvRepositories.isVisible = false
                    tvErrorMessage.isVisible = false
                }
            }

            is Result.Success -> {
                val repositories = result.repositories
                adapter.setData(repositories)
                binding.apply {
                    rvRepositories.isVisible = true
                    progressBar.isVisible = false
                    tvErrorMessage.isVisible = false
                }
            }

            is Result.Error -> {
                binding.apply {
                    tvErrorMessage.text = result.errorMessage
                    tvErrorMessage.isVisible = true
                    progressBar.isVisible = false
                    rvRepositories.isVisible = false
                }
            }
        }
    }

    private fun shareRepositoryLink(repositoryUrl: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT, repositoryUrl)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(intent, getString(R.string.share_repository))
        startActivity(shareIntent)
    }

    private fun openRepositoryLink(uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = uri
        }
        startActivity(intent)
    }

    private val iconShareClickListener: (Repository) -> Unit = {
        shareRepositoryLink(it.htmlUrl)
    }

    private val openRepositoryClickListener: (Repository) -> Unit = {
        openRepositoryLink(it.htmlUrl.toUri())
    }

    private fun openAboutDialog() {
        AboutDialog().show(supportFragmentManager, AboutDialog.TAG)
    }

}