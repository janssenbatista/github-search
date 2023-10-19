package dev.janssenbatista.githubsearch.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.janssenbatista.githubsearch.databinding.RepositoryListItemBinding
import dev.janssenbatista.githubsearch.models.Repository

class RepositoryAdapter(
    private val iconShareClickListener: (Repository) -> Unit,
    private val openRepositoryClickListener: (Repository) -> Unit
) :
    RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {

    private var repositories: List<Repository> = emptyList()

    fun setData(repositories: List<Repository>) {
        this.repositories = repositories
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RepositoryViewHolder(RepositoryListItemBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int = repositories.size

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class RepositoryViewHolder(private val binding: RepositoryListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val repository = repositories[position]
            binding.apply {
                tvRepoName.text = repository.name
                repository.description?.let {
                    tvRepoDescription.text = it
                } ?: {
                    tvRepoDescription.visibility = View.GONE
                }
                ivIconShare.setOnClickListener {
                    iconShareClickListener(repositories[position])
                }
                root.setOnClickListener {
                    openRepositoryClickListener(repositories[position])
                }
            }
        }
    }
}