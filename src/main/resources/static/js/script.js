document.addEventListener('DOMContentLoaded', () => {

	const $navbarBurgers = Array.prototype.slice.call(document.querySelectorAll('.navbar-burger'), 0);

	if ($navbarBurgers.length > 0) {

		$navbarBurgers.forEach(el => {
			el.addEventListener('click', () => {

				const target = el.dataset.target;
				const $target = document.getElementById(target);

				el.classList.toggle('is-active');
				$target.classList.toggle('is-active');

			});
		});
	}

});

function openModal(id) {
	let modal = document.querySelector('div.modal#modal-' + id);
	modal.classList.add('is-active');
}

function closeModal(id) {
	let modal = document.querySelector('div.modal#modal-' + id);
	modal.classList.remove('is-active');
}

function changeValor(object) {
	let valor = document.getElementById('valor');
	valor.value = parseFloat(object.options[object.selectedIndex].getAttribute('data-valor'));
}

function limparCamposEndereco() {
    document.getElementById('endereco.rua').value = ("");
    document.getElementById('endereco.bairro').value = ("");
    document.getElementById('endereco.cidade').value = ("");
}

function meu_callback(conteudo) {
    if (!("erro" in conteudo)) {
        document.getElementById('endereco.rua').value = (conteudo.logradouro);
        document.getElementById('endereco.bairro').value = (conteudo.bairro);
        document.getElementById('endereco.cidade').value = (conteudo.localidade);
    } else {
        limparCamposEndereco();
    }
}
    
function pesquisarCep(valor) {
    var cep = valor.replace(/\D/g, '');

    if (cep != "") {

        var validacep = /^[0-9]{8}$/;

        if(validacep.test(cep)) {

            document.getElementById('endereco.rua').value = "...";
            document.getElementById('endereco.bairro').value = "...";
            document.getElementById('endereco.cidade').value = "...";

            var script = document.createElement('script');
            script.src = 'https://viacep.com.br/ws/'+ cep + '/json/?callback=meu_callback';

            document.body.appendChild(script);

        } else {
            limparCamposEndereco();
        }
    } else {
        //cep sem valor, limpa formulário.
        limparCamposEndereco();
    }
};
