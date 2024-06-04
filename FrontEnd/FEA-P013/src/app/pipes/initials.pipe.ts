import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'initials',
  standalone: true
})
export class InitialsPipe implements PipeTransform {

  transform(value: string): string {
    if (!value || typeof value !== 'string') {
      return '';
    }

    const names = value.trim().split(' ').filter(name => name.length > 0);

    if (names.length === 0) {
      return '';
    }

    const firstNameInitial = names[0].charAt(0).toUpperCase();
    const lastNameInitial = names.length > 1 ? names[names.length - 1].charAt(0).toUpperCase() : '';

    return `${firstNameInitial}${lastNameInitial}`;
  }

}
