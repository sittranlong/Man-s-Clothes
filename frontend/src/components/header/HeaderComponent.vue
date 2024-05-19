<script lang="tsx">
import {defineComponent, ref, watch} from "vue";
import {RouterLink} from "vue-router";

export default defineComponent({
	name: 'HeaderComponent',
	components: {
		RouterLink
	},
	props: {
		showHeader: {
			type: Boolean,
			default: true,
		},
	},
	setup(props) {
		const isShow = ref(props.showHeader);
		const homeUri = ['/', '/home', '/index'];
		const productUri = ['/product', '/products', '/product/:id'];
		const aboutUri = ['/about'];
		const contactUri = ['/contact'];
		const cartUri = ['/cart'];

		const activeMenu = (uri: string[]) => {
			let currentUri = window.location.pathname;
			let active = false;
			uri.forEach((item) => {
				if (currentUri === item) {
					active = true;
				}
			});
			return active;
		};
		const scrollHandler = () => {
			let scroll = window.scrollY || document.documentElement.scrollTop;
			let header = document.querySelector('header')?.offsetHeight || 0;

			if (scroll >= header) {
				document.querySelector('header')?.classList.add('background-header');
			} else {
				document.querySelector('header')?.classList.remove('background-header');
			}
		};

		window.addEventListener('scroll', scrollHandler);
		watch(isShow, (value) => {
			console.log('isShow changed:', value);
			if (value) {
				// Thêm sự kiện scroll khi isShow là true
				window.addEventListener('scroll', scrollHandler);
			} else {
				// Xóa sự kiện scroll khi isShow là false
				window.removeEventListener('scroll', scrollHandler);
			}
		});
		return () => (
			<div>
				{isShow.value && (
					<header class="">
						<nav class="navbar navbar-expand-lg">
							<div class="container">
								<a class="navbar-brand" href="index.html"><h2>Template <em>Clothing</em></h2></a>
								<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
									<span class="navbar-toggler-icon"></span>
								</button>
								<div class="collapse navbar-collapse" id="navbarResponsive">
									<ul class="navbar-nav ml-auto">
										<li class="nav-item" role="button" title="home">
											<router-link to={"/home"} class={{'nav-link' : true,'active' : activeMenu(homeUri)}} id="home">Home</router-link>
										</li>
										<li class="nav-item" role="button" title="product">
											<router-link to={"/product"} class={{'nav-link' : true,'active' : activeMenu(productUri)}} id="product">Product</router-link>
										</li>
										<li class="nav-item" role="button" title="about">
											<router-link to={"/about"} class={{'nav-link' : true,'active' : activeMenu(aboutUri)}} id="about">About Us</router-link>
										</li>
										<li class="nav-item" role="button" title="contact">
											<router-link to={"/contact"} class={{'nav-link' : true,'active' : activeMenu(contactUri)}} id="contact">Contact Us</router-link>
										</li>
										<li class="nav-item" role="button" title="cart">
											<router-link to={"/cart"} class={{'nav-link' : true,'active' : activeMenu(cartUri)}} id="cart">
												<i class="bi bi-cart p-1"></i>
												Cart
											</router-link>
										</li>
									</ul>
								</div>
							</div>
						</nav>
					</header>
				)}
			</div>
		)
	}
});
</script>

<style lang="css" scoped>
@import "style.css";
</style>