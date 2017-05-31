$(".answer-write input[type=submit]").click(addAnswer);

function addAnswer(e) {
	/*click 이벤트의 기능을 차단하는 함수*/
	e.preventDefault();
	console.log("click me!");
	
	/*jquery 의 serialize() 메소드의 기능
	form 요소(컨트롤)들의 값을 한 번에 
	url parameter 형식 텍스트 문자열로 변환*/
	var queryString = 
	$(".answer-write").serialize();
	console.log("query: " + queryString);
	
	var url = $(".answer-write").attr("action");
	console.log("url: " + url);
	
	$.ajax({
		type: 'post',
		url: url,
		data: queryString,
		dataType: 'json',
		error: onError,
		success: onSuccess
	});
}

function onError() {

}

function onSuccess(data, status) {
/*컨솔창에 확인해보니 formattedCreateDate data만 전송받아 표시됨
다른 data 들은 전송받지 못함. - Answer 클래스에서 
formattedCreateDate data 는 getter 메소드로
data를 전송받았지만 다른 data 들은 getter 메소드가 
없기 때문에 전송받지 못함.*/
	console.log(data);
	
	//show.html 하단에 Html 페이지를 자바스크립트를 사용해서 
	//동적으로 변경하는 템플릿 answerTemplate 사용 
	var answerTemplate = 
	$("#answerTemplate").html();
	
	//answerTemplate 템플릿에 
	//String.prototype.format 함수를 사용하여
	//Html 페이지 내부에 text/template 타입의 
	//자바스크립트를 사용하여 동적으로 변경
	//크롬 브라우저 f12 눌러 console창에서 console.log(data)
	//결과를 읽고 show.html의 answerTemplate
	//템플릿에 {{숫자}}로 표시되는 매개인자들을 포함한다.
	var template = answerTemplate.format(
	data.writer.userId, data.formattedCreateDate,
	data.contents, data.question.id, data.id);
	//답변을 추가하는 부분에 대해서만 템플릿 적용하여 동적으로 변경
	$(".qna-comment-slipp-articles").prepend(template);
	
	//answer-write 라는 class 이름을 가진 form 태그의 
	//하위의 textarea 태그의 내용을 공백처리
	$(".answer-write textarea").val("");
	//기능 동일
	/*$("textarea[name=contents]").val("");*/
}

String.prototype.format = function() {
	var args = arguments;
	//replace를 정규표현식과 쓸 수 있다.
    ///{(\d+)}/g : g글로벌 범위로 {}안의 \d숫자가 
	//{0,1...}번 있는 것을 찾음
	return this.replace(/{(\d+)}/g, 
	function(match, number) {
		return typeof args[number] != 
		'undefined' ? args[number] : match;
	});
};