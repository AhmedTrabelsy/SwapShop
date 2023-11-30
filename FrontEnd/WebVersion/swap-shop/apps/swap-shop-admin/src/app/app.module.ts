import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { appRoutes } from './app.routes';
import { NxWelcomeComponent } from './nx-welcome.component';
import { SidebarComponent } from './shared/sidebar/sidebar.component';
import { ShellComponent } from './shared/shell/shell.component';

@NgModule({
	declarations: [AppComponent, NxWelcomeComponent, SidebarComponent, ShellComponent],
	imports: [BrowserModule, RouterModule.forRoot(appRoutes)],
	providers: [],
	bootstrap: [AppComponent],
	exports: [
   SidebarComponent,
   ShellComponent
	]
})
export class AppModule {}
