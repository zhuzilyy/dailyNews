$(function() {
	$(".news-banner-container").remove();
	$(".recommendation-container-new-article-test").closest("div").remove();
	$(".bottom-banner-container").closest("div").remove();
	$(".unflod-field__mask").click();
});

/*
function removeDoc() {
*/
/*	$(".news-banner-container").remove();
	$(".recommendation-container-new-article-test").closest("div").remove();
	$(".bottom-banner-container").closest("div").remove();
	$(".unflod-field__mask").click();

	$("p[data-node=appName]").closest("body").find("div").first().remove();
	$("div.app-download").click();
	$("div.ans-loadmore").remove();
	$("div.recommend-feed").remove();*//*


	$("body").remove();
}

$(function() {
	removeDoc();
	setInterval(function() {
		removeDoc();
	}, 200)
});*/