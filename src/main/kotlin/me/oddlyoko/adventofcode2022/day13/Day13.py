data = [line.rstrip('\n') for line in open("13").readlines()]

map = []
# Parse the file
for i in range(int((len(data) + 1) / 3)):
    first_instruction = eval(data[i * 3])
    second_instruction = eval(data[i * 3 + 1])
    map.append((first_instruction, second_instruction))

def check(left, right):
    if isinstance(left, int) and isinstance(right, int):
        return -1 if left < right else 1 if left > right else 0
    if isinstance(left, list) and isinstance(right, list):
        for l, r in zip(left, right):
            # If there is still element on left,
            result_check = check(l, r)
            if result_check != 0:
                return result_check
        return check(len(left), len(right))
    if isinstance(left, list):
        return check(left, [right])
    return check([left], right)

counter = 0
# Do the action
for idx, (left_instruction, right_instruction) in enumerate(map):
    result = check(left_instruction, right_instruction)
    if result != 1:
        counter += idx + 1

print(f"Part 1: {counter}")

# Part 2
import functools

unsorted_instructions = [instruction for instructions in map for instruction in instructions]
unsorted_instructions += [[[2]], [[6]]]
sorted_instructions = sorted(unsorted_instructions, key=functools.cmp_to_key(check))
# Find [[2]] and [[6]]
two_six = [idx + 1 for idx, instruction in enumerate(sorted_instructions) if str(instruction) == "[[2]]" or str(instruction) == "[[6]]"]
print(f"Part 2: {two_six[0] * two_six[1]}")
