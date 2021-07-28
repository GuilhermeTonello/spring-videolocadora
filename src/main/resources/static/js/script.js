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
