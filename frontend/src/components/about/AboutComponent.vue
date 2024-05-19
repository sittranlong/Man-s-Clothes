<script lang="tsx">
import {defineComponent} from "vue";
import LoadingButtonComponent from "@/components/button/LoadingButtonComponent.vue";

export default defineComponent({
	name: 'AboutComponent',
	components: {
		LoadingButtonComponent
	},
	setup() {
		return () => (
			<div class="about-features">
				<div class="best-features">
					<div class="container">
						<div class="row">
							<div class="col-md-12">
								<div class="section-heading">
									<h2>About template</h2>
								</div>
							</div>
							<div class="col-md-6">
								<div class="left-content">
									<h4>Looking for the best products?</h4>
									<p><a rel="nofollow" href="https://github.com/pudo58" target="_blank">This
										template</a> is free by <b>pudo58</b></p>
									<ul class="featured-list">
										<li><a href="#">Lorem ipsum dolor sit amet</a></li>
										<li><a href="#">Consectetur an adipisicing elit</a></li>
										<li><a href="#">It aquecorporis nulla aspernatur</a></li>
										<li><a href="#">Corporis, omnis doloremque</a></li>
										<li><a href="#">Non cum id reprehenderit</a></li>
									</ul>
									<loading-button-component text="Read More" loading={false}/>
								</div>
							</div>
							<div class="col-md-6">
								<div class="right-image">
									<img src="src/assets/images/feature-image.jpg" alt=""/>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		)
	}
});
</script>

<style scoped lang="css">
@import "style.css";
</style>