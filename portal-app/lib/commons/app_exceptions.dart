class AppBaseException implements Exception {
  final String message;

  AppBaseException(this.message);
}

class NotFoundException extends AppBaseException {
  NotFoundException(super.message);
}
