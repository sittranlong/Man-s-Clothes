<script lang="tsx">
import {defineComponent,ref} from "vue";
export default defineComponent({
	name : 'OurCustomerComponent',
	setup() {
		const images = [
			'src/assets/images/client-01.png',
			'src/assets/images/client-01.png',
			'src/assets/images/client-01.png',
			'src/assets/images/client-01.png',
			'src/assets/images/client-01.png',
			'src/assets/images/client-01.png',
		]
		return () => (
			<div class="happy-clients">
				<div class="container">
					<div class="row">
						<div class="col-md-12">
							<div class="section-heading">
								<h2>Our Happy Customers</h2>
							</div>
						</div>
						<div class="col-md-12">
							<div class="d-flex justify-content-center">
								{images?.map((item,index) => (
									<div class="client-item m-1" key={index}>
										<img src={item} alt="client"/>
									</div>
								))}
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