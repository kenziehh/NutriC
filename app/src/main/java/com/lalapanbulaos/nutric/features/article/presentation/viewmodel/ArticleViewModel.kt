package com.lalapanbulaos.nutric.features.article.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lalapanbulaos.nutric.core.models.Article
import com.lalapanbulaos.nutric.features.article.data.repository.ArticleRepository
import com.lalapanbulaos.nutric.features.article.usecase.GetArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ArticleUiState())
    val uiState: StateFlow<ArticleUiState> = _uiState.asStateFlow()

    private val _inputState = MutableStateFlow(ArticleInputState())
    val inputState: StateFlow<ArticleInputState> = _inputState.asStateFlow()

    // Separate flow for search queries
    private val searchQueryFlow = MutableStateFlow("")

    init {
        fetchArticles()
        setupSearchDebounce()
    }

    @OptIn(FlowPreview::class)
    private fun setupSearchDebounce() {
        viewModelScope.launch {
            searchQueryFlow
                .debounce(300) // Wait 300ms after last typing
                .distinctUntilChanged() // Only emit if the query has changed
                .collect { query ->
                    fetchArticles(query.takeIf { it.isNotBlank() })
                }
        }
    }

    fun fetchSingleArticle(articleId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val result = getArticlesUseCase.executeSingleArticle(articleId)

            _uiState.value = _uiState.value.copy(
                selectedArticle = result.getOrNull(),
                isLoading = false,
                error = result.exceptionOrNull()?.message
            )
        }
    }

    fun fetchArticles(query: String? = null) {
        viewModelScope.launch {
            // Update loading state before fetching
            _uiState.value = _uiState.value.copy(isLoading = true)

            val result = getArticlesUseCase.execute(query)

            // Update state based on result
            _uiState.value = _uiState.value.copy(
                articles = result.getOrDefault(emptyList()),
                isLoading = false,
                error = result.exceptionOrNull()?.message
            )
        }
    }

    fun updateSearchQuery(query: String) {
        // Update input state
        _inputState.value = _inputState.value.copy(searchQuery = query)

        // Emit to search query flow for debouncing
        searchQueryFlow.value = query
    }

    fun onSelectSingleArticle(article: Article) {
        _uiState.value = _uiState.value.copy(selectedArticle = article)
    }
}

data class ArticleUiState(
    val selectedArticle: Article? = null,
    val articles: List<Article> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false
)

data class ArticleInputState(
    val searchQuery: String = "",
)