const stripe = Stripe('pk_test_51Pv9EXRopxX1etdHzZttNbnYsg8oSmZBfAEyZfHUaDabBazuhmtBWZFFsk99VqwTfkNHU4wDKrqBYtEVvbgNjRpN00kLWtdxTj');
const paymentButton = document.querySelector('#paymentButton');

paymentButton.addEventListener('click',()=>{
	stripe.redirectToCheckout({
		sessionId: sessionId
	})
});