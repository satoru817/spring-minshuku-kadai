const imageInput = document.getElementById('imageFile');
const imagePreview = document.getElementById('imagePreview');

imageInput.addEventListener('change',()=>{
	if(imageInput.files[0]){
		let fileReader = new FileReader();
		fileReader.onload=()=>{//onloadは読み込めたときに発火する
			imagePreview.innerHTML = `<img src="${fileReader.result}" class="mb-3">`;//fileReader.resultには読み込んだファイルの内容が格納されている
		}
		fileReader.readAsDataURL(imageInput.files[0]);//ここで読み込みを試みているDataURLとはデータをテキスト形式で埋め込むもの
	}else{
		imagePreview.innerHTML='';
	}
})