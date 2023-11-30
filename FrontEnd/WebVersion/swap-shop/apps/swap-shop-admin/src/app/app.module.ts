import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { appRoutes } from './app.routes';
import { NxWelcomeComponent } from './nx-welcome.component';
import { SidebarComponent } from './shared/sidebar/sidebar.component';

@NgModule({
	declarations: [AppComponent, NxWelcomeComponent, SidebarComponent],
	imports: [BrowserModule, RouterModule.forRoot(appRoutes)],
	providers: [],
	bootstrap: [AppComponent],
	exports: [
   SidebarComponent
	]
})
export class AppModule {}
