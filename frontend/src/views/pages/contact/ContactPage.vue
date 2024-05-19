<script lang="tsx">
import {defineComponent, reactive} from "vue";
import HeaderComponent from "@/components/header/HeaderComponent.vue";
import LoadingButtonComponent from "@/components/button/LoadingButtonComponent.vue";
import FooterComponent from "@/components/footer/FooterComponent.vue";
import {QuestionModel} from "@/base/model/question.model";
import LoadingComponent from "@/components/loading/LoadingComponent.vue";
import {ContactModel} from "@/base/model/contact.model";

export default defineComponent({
	name: 'ContactPage',
	components: {
		HeaderComponent,
		LoadingButtonComponent,
		FooterComponent,
		LoadingComponent
	},
	setup() {
		const questionDataLoader = [
			{
				id: "demo",
				question: 'Question 1',
				answer: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Itaque corporis amet elite author nulla.',
			},
			{
				id: "demo1",
				question: 'Question 2',
				answer: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Itaque corporis amet elite author nulla.',
			},
			{
				id: "demo2",
				question: 'Question 3',
				answer: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Itaque corporis amet elite author nulla.',
			}
		] as QuestionModel[];
		const loadingComponent = <LoadingComponent loading={true}/>

		const contact = (
			<div class="page-heading contact-heading header-text">
				<div class="container">
					<div class="row">
						<div class="col-md-12">
							<div class="text-content">
								<h4>contact us</h4>
								<h2>let’s get in touch</h2>
							</div>
						</div>
					</div>
				</div>
			</div>
		);
		let contactData = reactive({
			fullName: '',
			email: '',
			subject: '',
			message: ''
		} as ContactModel);
		const setValue = ($event: any) => {
			contactData = {
				...contactData,
				[$event.target.id]: $event.target.value
			}
		};
		const sendMessage = (
			<div class="send-message">
				<div class="container">
					<div class="row">
						<div class="col-md-12">
							<div class="section-heading">
								<h2>Send us a Message</h2>
							</div>
						</div>
						<div class="col-md-8">
							<div class="contact-form">
								<form id="contact" class='border-1'>
									<div class="row">
										<div class="col-lg-12 col-md-12 col-sm-12">
											<fieldset>
												<input onChange={($event) => setValue($event)} type="text" class="form-control" id="fullName" placeholder="Full Name"/>
											</fieldset>
										</div>
										<div class="col-lg-12 col-md-12 col-sm-12">
											<fieldset>
												<input onChange={($event) => setValue($event)} type="email" class="form-control" id="email" placeholder="E-Mail Address"/>
											</fieldset>
										</div>
										<div class="col-lg-12 col-md-12 col-sm-12">
											<fieldset>
												<input onChange={($event) => setValue($event)} type="text" class="form-control" id="subject" placeholder="Subject"/>
											</fieldset>
										</div>
										<div class="col-lg-12">
											<fieldset>
												<textarea onChange={($event) => setValue($event)} rows="6" class="form-control" id="message" placeholder="Your Message"></textarea>
											</fieldset>
										</div>
										<div class="col-lg-12">
											<fieldset>
												<LoadingButtonComponent onclick={() => console.log(contactData)} text="Send Message" loading={false}/>
											</fieldset>
										</div>
									</div>
								</form>
							</div>
						</div>
						<div class="col-md-4">
							<ul class="accordion accordion-flush">
								{questionDataLoader.map((item) => (
									<li class="accordion-item">
										<div class="accordion-header">
											<a class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target={`#${item.id}`} aria-expanded="false" aria-controls="flush-collapseOne">
												{item?.question}
											</a>
										</div>
										<div class="accordion-body accordion-collapse collapse" id={item.id}>
											<p>
												{item?.answer}
											</p>
										</div>
									</li>
								))}
							</ul>
						</div>
					</div>
				</div>
			</div>
		);
		const map = (<div class="find-us">
				<div class="container">
					<div class="row">
						<div class="col-md-12">
							<div class="section-heading">
								<h2>Our Location on Maps</h2>
							</div>
						</div>
						<div class="col-md-8">
							<div id="map">
                                <iframe
                                    src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d238893.92637309415!2d106.1892875522188!3d20.681065075333105!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31358ac52731c375%3A0x8d3283fc01cc9bf!2zVsSpbmggQuG6o28sIEjhuqNpIFBow7JuZywgVmnhu4d0IE5hbQ!5e0!3m2!1svi!2s!4v1704857951149!5m2!1svi!2s"
                                    width="100%"
                                    height="330px" style="border:0"
                                    allowfullscreen></iframe>
							</div>
						</div>
						<div class="col-md-4">
							<div class="left-content">
								<h4>About our office</h4>
								<p>Lorem ipsum dolor sit amet, consectetur adipisic elit. Sed voluptate nihil eumester
									consectetur similiqu consectetur.<br/><br/>Lorem ipsum dolor sit amet, consectetur
									adipisic elit. Et, consequuntur, modi mollitia corporis ipsa voluptate corrupti.</p>
								<ul class="social-icons">
									<li><a href="#"><i class="fa fa-facebook"></i></a></li>
									<li><a href="#"><i class="fa fa-twitter"></i></a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		);
		return () => (
			<div>
				{loadingComponent}
				<header-component/>
				{contact}
				{map}
				{sendMessage}
				<footer-component/>
			</div>
		)
	}
})
</script>