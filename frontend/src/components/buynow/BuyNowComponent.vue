<script lang="tsx">
import {defineComponent} from "vue";
import LoadingButtonComponent from "@/components/button/LoadingButtonComponent.vue";
export default defineComponent({
	name : 'BuyNowComponent',
	setup(){
		return () => (
			<div>
				<div class="call-to-action">
					<div class="container">
						<div class="row">
							<div class="col-md-12">
								<div class="inner-content">
									<div class="row">
										<div class="col-md-8">
											<h4>Creative &amp; Unique <em>Template</em> Products</h4>
											<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Itaque corporis amet elite author nulla.</p>
										</div>
										<div class="col-md-4">
											<LoadingButtonComponent text="Buy Now" loading={false}/>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		)
	}
})
</script>

<style scoped lang="css">
@import "style.css";
</style>