import { Pipe, PipeTransform } from '@angular/core';
import { DatePipe } from '@angular/common';

@Pipe({
  name: 'releaseDate'
})
export class ReleaseDatePipe implements PipeTransform {
  transform(value: string): string {
    if (!value) return '';
    
    // Extraemos solo la parte de la fecha (YYYY-MM-DD)
    const datePart = value.split('T')[0];
    const [year, month, day] = datePart.split('-');
    
    return `${year}-${month}-${day}`;
    
    // O si prefieres otro formato, puedes usar DatePipe:
    // return new DatePipe('en-US').transform(datePart, 'yyyy-MM-dd');
  }
}