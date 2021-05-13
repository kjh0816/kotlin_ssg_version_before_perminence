class ArticleRepository {




    fun addArticle(memberId: Int, title: String, body: String, boardId: Int) {

        val id = ++articleLastId
        val regDate = Util.getNowDateStr()
        val updateDate = Util.getNowDateStr()

        articles.add(Article(id,regDate, updateDate, memberId, boardId, title, body))



    }

    fun getArticleById(id: Int): Article? {
        for(article in articles){
            if(article.id == id){
                return article
            }
        }
        return null


    }

    fun removeArticle(article: Article) {
        articles.remove(article)


    }

    fun modifyArticle(article: Article, title: String, body: String) {

        article.title = title
        article.body = body
        article.updateDate = Util.getNowDateStr()


    }

    fun articleDetail(article: Article?) {

        val board = boardRepository.getBoardById(article!!.boardId)
        val member = memberRepository.getMemberById(article.memberId)

        println("번호      : ${article.id}")
        println("게시판    : ${board!!.name}")
        println("제목      : ${article.title}")
        println("내용      : ${article.body}")
        println("작성자    : ${member!!.nickname}")
        println("최초 작성일: ${article.regDate}")
        println("수정일자   : ${article.updateDate}")
    }

    fun getFilteredArticle(searchKeyword: String, page: Int, boardCode: String, itemCountInAPage: Int) {
        val filtered1Articles = getSearchKeywordFilteredArticles(articles, searchKeyword, boardCode)
        val filtered2Articles = getPageFilteredArticles(filtered1Articles, page, itemCountInAPage)
    }



    private fun getSearchKeywordFilteredArticles(articles: List<Article>, searchKeyword: String, boardCode: String): List<Article> {
        val filteredArticles = mutableListOf<Article>()


        val boardId = boardRepository.getBoardIdByCode(boardCode)

        if(searchKeyword.isNotEmpty() && boardId != 0){

            for(article in articles){
                if(article.title.contains(searchKeyword) && article.boardId == boardId){
                    filteredArticles.add(article)
                }
            }
            return filteredArticles
        }
        else if(searchKeyword.isNotEmpty() && boardId == 0) {
            for (article in articles) {
                if (article.title.contains(searchKeyword)) {
                    filteredArticles.add(article)
                }
            }
            return filteredArticles
        }
        else if(searchKeyword.isEmpty() && boardId != 0){
            for(article in articles){
                if(article.boardId == boardId){
                    filteredArticles.add(article)
                }
            }
            return filteredArticles
        }

        return articles
    }
    fun getPageFilteredArticles(
        filtered1Articles: List<Article>,
        page: Int,
        itemCountInAPage: Int
    ) {

        val filteredArticles = mutableListOf<Article>()

        val offsetCount = (page - 1) * itemCountInAPage

        val startIndex = filtered1Articles.lastIndex - offsetCount
        var endIndex = startIndex - (itemCountInAPage - 1)

        if(endIndex < 0){
            endIndex = 0
        }

        for(i in startIndex downTo endIndex){
            filteredArticles.add(filtered1Articles[i])
        }


        for(article in filteredArticles){
            val member = memberRepository.getMemberById(article.memberId)
            println("${article.id} / ${member!!.nickname} / ${article.title} / ${article.body} / ${article.updateDate}")


        }

    }

        var articleLastId = 0
        val articles = mutableListOf<Article>()



}