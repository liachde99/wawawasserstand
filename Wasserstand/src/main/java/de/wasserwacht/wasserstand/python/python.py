from de.wasserwacht.wasserstand.python import PythonService
class PythonServiceP(PythonService):
    def __init__(self):
        self.value="Hello from python"

    def python(self):
        return self.value